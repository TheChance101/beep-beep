package org.thechance.service_identity.data.geteway

import com.mongodb.MongoWriteException
import com.mongodb.client.model.Filters
import com.mongodb.client.model.FindOneAndUpdateOptions
import com.mongodb.client.model.IndexOptions
import com.mongodb.client.model.Indexes
import com.mongodb.client.model.ReturnDocument
import com.mongodb.client.model.Sorts
import com.mongodb.client.model.Updates
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.bson.types.ObjectId
import org.koin.core.annotation.Single
import org.litote.kmongo.addToSet
import org.litote.kmongo.and
import org.litote.kmongo.bitsAllSet
import org.litote.kmongo.coroutine.aggregate
import org.litote.kmongo.eq
import org.litote.kmongo.inc
import org.litote.kmongo.lookup
import org.litote.kmongo.match
import org.litote.kmongo.or
import org.litote.kmongo.pull
import org.litote.kmongo.regex
import org.litote.kmongo.set
import org.litote.kmongo.setTo
import org.litote.kmongo.setValue
import org.thechance.service_identity.data.DataBaseContainer
import org.thechance.service_identity.data.collection.AddressCollection
import org.thechance.service_identity.data.collection.DetailedUserCollection
import org.thechance.service_identity.data.collection.UserCollection
import org.thechance.service_identity.data.collection.UserDetailsCollection
import org.thechance.service_identity.data.collection.WalletCollection
import org.thechance.service_identity.data.collection.mappers.toCollection
import org.thechance.service_identity.data.collection.mappers.toEntity
import org.thechance.service_identity.data.collection.mappers.toManagedEntity
import org.thechance.service_identity.data.collection.mappers.toUserEntity
import org.thechance.service_identity.data.util.isUpdatedSuccessfully
import org.thechance.service_identity.data.util.paginate
import org.thechance.service_identity.domain.entity.Address
import org.thechance.service_identity.domain.entity.Location
import org.thechance.service_identity.domain.entity.User
import org.thechance.service_identity.domain.entity.UserManagement
import org.thechance.service_identity.domain.entity.UserOptions
import org.thechance.service_identity.domain.entity.Wallet
import org.thechance.service_identity.domain.gateway.IDataBaseGateway
import org.thechance.service_identity.domain.security.SaltedHash
import org.thechance.service_identity.domain.util.ERROR_IN_DB
import org.thechance.service_identity.domain.util.NOT_FOUND
import org.thechance.service_identity.domain.util.ResourceNotFoundException
import org.thechance.service_identity.domain.util.USER_ALREADY_EXISTS
import org.thechance.service_identity.domain.util.USER_NOT_FOUND
import org.thechance.service_identity.domain.util.UserAlreadyExistsException
import org.thechance.service_identity.domain.util.getCountryForLocation

@Single
class DataBaseGateway(private val dataBaseContainer: DataBaseContainer) : IDataBaseGateway {

    init {
        CoroutineScope(Dispatchers.IO).launch { createUniqueIndexIfNotExists() }
    }

    //region Address
    override suspend fun addLocation(userId: String, location: Location): Address {
        val address = AddressCollection(
            userId = ObjectId(userId),
            location = location.toCollection()
        )
        dataBaseContainer.userDetailsCollection.updateOne(
            filter = UserDetailsCollection::userId eq ObjectId(userId),
            update = Updates.addToSet(UserDetailsCollection::addressIds.name, address.id)
        )
        return if (dataBaseContainer.addressCollection.insertOne(address).wasAcknowledged()) {
            val addresses = getUserAddresses(userId)
            if (addresses.size == 1) {
                updateUserCountry(userId, getUserCountry(userId))
            }
            address.toEntity()
        } else {
            throw ResourceNotFoundException(ERROR_IN_DB)
        }
    }

    override suspend fun addAddress(userId: String, address: Address): Address {
        val addressCollection = AddressCollection(
            userId = ObjectId(userId),
            address = address.address,
            location = address.location.toCollection()
        )
        dataBaseContainer.userDetailsCollection.updateOne(
            filter = UserDetailsCollection::userId eq ObjectId(userId),
            update = Updates.addToSet(UserDetailsCollection::addressIds.name, address.id)
        )
        return if (dataBaseContainer.addressCollection.insertOne(addressCollection)
                .wasAcknowledged()
        ) {
            val addresses = getUserAddresses(userId)
            if (addresses.size == 1) {
                updateUserCountry(userId, getUserCountry(userId))
            }
            addressCollection.toEntity()
        } else {
            throw ResourceNotFoundException(ERROR_IN_DB)
        }
    }

    override suspend fun deleteAddress(id: String): Boolean {
        return dataBaseContainer.addressCollection.updateOne(
            filter = Filters.and(
                AddressCollection::id eq ObjectId(id),
                AddressCollection::isDeleted eq false
            ),
            update = setValue(AddressCollection::isDeleted, true)
        ).isUpdatedSuccessfully()
    }

    override suspend fun updateAddress(addressId: String, location: Location): Address {
        return dataBaseContainer.addressCollection.findOneAndUpdate(
            filter = AddressCollection::id eq ObjectId(addressId),
            update = Updates.set(AddressCollection::location.name, location.toCollection()),
            options = FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER)
        )?.toEntity() ?: throw ResourceNotFoundException(NOT_FOUND)
    }

    override suspend fun getAddress(id: String): Address {
        return dataBaseContainer.addressCollection.findOne(
            AddressCollection::id eq ObjectId(id),
            AddressCollection::isDeleted eq false
        )?.toEntity() ?: throw ResourceNotFoundException(NOT_FOUND)
    }

    override suspend fun getUserAddresses(userId: String): List<Address> {
        return dataBaseContainer.addressCollection.find(
            AddressCollection::userId eq ObjectId(userId),
            AddressCollection::isDeleted eq false
        ).toList().toEntity()
    }

    override suspend fun getUserCountry(userId: String): String {
        val userAddresses = getUserAddresses(userId)
        val location = userAddresses.firstOrNull()?.location
        return getCountryForLocation(location)
    }

    override suspend fun updateUserCountry(userId: String, country: String): Boolean {
        try {
            dataBaseContainer.userCollection.find(filter = (UserCollection::id eq ObjectId(userId)))
            return dataBaseContainer.userCollection.updateOneById(
                ObjectId(userId),
                set(UserCollection::country setTo country),
                updateOnlyNotNullProperties = true
            ).isUpdatedSuccessfully()
        } catch (exception: MongoWriteException) {
            throw UserAlreadyExistsException(USER_ALREADY_EXISTS)
        }
    }

    //endregion

    //region User
    private suspend fun createUniqueIndexIfNotExists() {
        if (!isUniqueIndexCreated()) {
            val indexOptions = IndexOptions().unique(true)
            dataBaseContainer.userCollection.createIndex(
                Indexes.ascending(DataBaseContainer.USER_NAME),
                indexOptions
            )
        }
    }

    private suspend fun isUniqueIndexCreated(): Boolean {
        val indexName = DataBaseContainer.INDEX_NAME
        val indexInfo = dataBaseContainer.userCollection.listIndexes<Indexes>().toList()
            .filter { it.equals(indexName) }

        return indexInfo.isNotEmpty()
    }

    override suspend fun getUserById(id: String): User {
        val wallet = getWalletByUserId(id)
        val userAddresses = getUserAddresses(id)
        val userPermission = getUserPermission(id)
        val location = userAddresses.firstOrNull()?.location
        val country = getCountryForLocation(location)
        return dataBaseContainer.userCollection.aggregate<DetailedUserCollection>(
            match(
                UserCollection::id eq ObjectId(id),
                UserCollection::isDeleted eq false
            ),
            lookup(
                localField = UserCollection::id.name,
                from = DataBaseContainer.USER_DETAILS_COLLECTION,
                foreignField = "_id",
                newAs = DetailedUserCollection::details.name
            )
        ).toList().toUserEntity(
            wallet.walletBalance,
            wallet.currency,
            userAddresses,
            country,
            userPermission
        ).firstOrNull()
            ?: throw ResourceNotFoundException(NOT_FOUND)
    }



        override suspend fun getUsers(options: UserOptions): List<UserManagement> {
            val searchQuery = or(
                options.query?.let { UserCollection::fullName regex it },
                options.query?.let { UserCollection::username regex it }
            )

            val orConditions = options.permissions?.map { permission ->
                or(
                    UserCollection::permission eq permission,
                    UserCollection::permission.bitsAllSet(permission.toLong()) // Convert to Long
                )
            }

            return dataBaseContainer.userCollection.find(
                and(
                    searchQuery,
                    orConditions?.let { or(*orConditions.toTypedArray()) },
                    UserCollection::isDeleted eq false,
                )
            ).projection(
                UserCollection::id,
                UserCollection::fullName,
                UserCollection::username,
                UserCollection::email,
                UserCollection::country,
                UserCollection::permission,
            ).paginate(options.page, options.limit).toList().toManagedEntity()
        }

        override suspend fun createUser(
            saltedHash: SaltedHash, fullName: String, username: String, email: String
        ): UserManagement {
            val userNameExist =
                dataBaseContainer.userCollection.findOne(UserCollection::username eq username)
            if (userNameExist == null) {
                val userDocument = UserCollection(
                    hashedPassword = saltedHash.hash,
                    salt = saltedHash.salt,
                    username = username,
                    fullName = fullName,
                    email = email
                )
                val wallet = WalletCollection(userId = userDocument.id)
                createWallet(wallet)
                dataBaseContainer.userDetailsCollection.insertOne(UserDetailsCollection(userId = userDocument.id))
                dataBaseContainer.userCollection.insertOne(userDocument)
                return userDocument.toManagedEntity()

            } else {
                throw UserAlreadyExistsException(USER_ALREADY_EXISTS)
            }
        }

        override suspend fun updateUser(
            id: String,
            saltedHash: SaltedHash?,
            fullName: String?,
            username: String?,
            email: String?
        ): Boolean {
            try {
                dataBaseContainer.userCollection.find(filter = (UserCollection::username eq username))
                return dataBaseContainer.userCollection.updateOneById(
                    ObjectId(id),
                    set(
                        UserCollection::hashedPassword setTo saltedHash?.hash,
                        UserCollection::salt setTo saltedHash?.salt,
                        UserCollection::username setTo username,
                        UserCollection::fullName setTo fullName,
                        UserCollection::email setTo email,
                    ),
                    updateOnlyNotNullProperties = true
                ).isUpdatedSuccessfully()
            } catch (exception: MongoWriteException) {
                throw UserAlreadyExistsException(USER_ALREADY_EXISTS)
            }
        }

        override suspend fun updateUserProfile(
            id: String, fullName: String?,
        ): Boolean {
            try {
                dataBaseContainer.userCollection.find(filter = (UserCollection::id eq ObjectId(id)))
                return dataBaseContainer.userCollection.updateOneById(
                    ObjectId(id),
                    set(
                        UserCollection::fullName setTo fullName,
                    ),
                    updateOnlyNotNullProperties = true
                ).isUpdatedSuccessfully()
            } catch (exception: MongoWriteException) {
                throw UserAlreadyExistsException(USER_ALREADY_EXISTS)
            }
        }

        override suspend fun deleteUser(id: String): Boolean {
            return dataBaseContainer.userCollection.updateOne(
                filter = UserCollection::id eq ObjectId(id),
                update = set(UserCollection::isDeleted setTo true)
            ).isUpdatedSuccessfully()
        }

        override suspend fun getNumberOfUsers(): Long {
            return dataBaseContainer.userCollection.countDocuments(UserCollection::isDeleted eq false)
        }

        override suspend fun isUserDeleted(id: String): Boolean {
            val user = dataBaseContainer.userCollection.findOne(
                UserCollection::id eq ObjectId(id),
                UserCollection::isDeleted eq true
            )
            return user != null
        }

        override suspend fun getUserByUsername(username: String): UserManagement {
            return dataBaseContainer.userCollection.findOne(
                UserCollection::username eq username,
                UserCollection::isDeleted eq false
            )?.toManagedEntity() ?: throw ResourceNotFoundException(USER_NOT_FOUND)
        }

        override suspend fun getLastRegisterUser(limit: Int): List<UserManagement> {
            return dataBaseContainer.userCollection.find(
                UserCollection::isDeleted eq false
            ).sort(Sorts.descending("_id")).limit(limit).toList().toManagedEntity()
        }

        override suspend fun searchUsers(
            searchTerm: String,
            filterByPermission: List<Int>
        ): List<UserManagement> {
            val orConditions = filterByPermission.map { permission ->
                or(
                    UserCollection::permission eq permission,
                    UserCollection::permission.bitsAllSet(permission.toLong()) // Convert to Long
                )
            }

            return dataBaseContainer.userCollection.find(
                and(
                    or(
                        UserCollection::username.regex("^$searchTerm", "i"),
                        UserCollection::email.regex("^$searchTerm", "i")
                    ),
                    or(*orConditions.toTypedArray()),
                    UserCollection::isDeleted eq false
                )
            ).toList().toManagedEntity() ?: throw ResourceNotFoundException(NOT_FOUND)
        }

        //endregion

        // region: wallet
        private suspend fun getWalletByUserId(userId: String): WalletCollection {
            return dataBaseContainer.walletCollection.findOne(
                WalletCollection::userId eq ObjectId(userId)
            ) ?: throw ResourceNotFoundException(NOT_FOUND)
        }

        override suspend fun subtractFromWallet(userId: String, amount: Double): Wallet {
            return dataBaseContainer.walletCollection.findOneAndUpdate(
                filter = WalletCollection::userId eq ObjectId(userId),
                update = inc(WalletCollection::walletBalance, -amount),
                options = FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER)
            )?.toEntity() ?: throw ResourceNotFoundException(NOT_FOUND)
        }

        override suspend fun getWalletBalance(userId: String): Wallet {
            return dataBaseContainer.walletCollection.findOne(
                WalletCollection::userId eq ObjectId(userId),
            )?.toEntity() ?: throw ResourceNotFoundException(NOT_FOUND)
        }

        override suspend fun addToWallet(userId: String, amount: Double): Wallet {
            return dataBaseContainer.walletCollection.findOneAndUpdate(
                filter = WalletCollection::userId eq ObjectId(userId),
                update = inc(WalletCollection::walletBalance, amount),
                options = FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER)
            )?.toEntity() ?: throw ResourceNotFoundException(NOT_FOUND)
        }

        override suspend fun updateWalletCurrency(userId: String, currency: String) {
            dataBaseContainer.walletCollection.findOneAndUpdate(
                filter = WalletCollection::userId eq ObjectId(userId),
                update = Updates.set(WalletCollection::currency.name, currency),
                options = FindOneAndUpdateOptions().upsert(true)
            )
        }

        private suspend fun createWallet(wallet: WalletCollection): Boolean {
            dataBaseContainer.userDetailsCollection.updateOne(
                filter = UserDetailsCollection::userId eq wallet.userId,
                update = set(UserDetailsCollection::walletCollection setTo wallet)
            )
            return dataBaseContainer.walletCollection.insertOne(wallet).wasAcknowledged()
        }

        // endregion: wallet

        // region: user permission management

        override suspend fun updateUserPermission(
            userId: String,
            permissions: Int
        ): UserManagement {

            return dataBaseContainer.userCollection.findOneAndUpdate(
                filter = UserCollection::id eq ObjectId(userId),
                update = Updates.set(UserCollection::permission.name, permissions),
                options = FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER)
            )?.toManagedEntity() ?: throw ResourceNotFoundException(USER_NOT_FOUND)
        }

        override suspend fun getUserPermission(userId: String): Int {
            return dataBaseContainer.userCollection.findOneById(ObjectId(userId))?.permission ?: 1
        }

        override suspend fun getUserPermissionByUsername(username: String): Int {
            return dataBaseContainer.userCollection.findOne(UserCollection::username eq username)?.permission
                ?: 1
        }
        // endregion: user permission management

        // region Token

        override suspend fun getSaltedHash(username: String): SaltedHash {
            val user = dataBaseContainer.userCollection.findOne(
                UserCollection::username eq username
            ) ?: throw ResourceNotFoundException(USER_NOT_FOUND)
            return SaltedHash(user.hashedPassword!!, user.salt!!)
        }
        // endregion

        // region: favorite
        override suspend fun getFavoriteRestaurants(userId: String): List<String> {
            val user = dataBaseContainer.userDetailsCollection.findOne(
                UserDetailsCollection::userId eq ObjectId(userId)
            )
            return user?.favorite?.map(ObjectId::toString) ?: emptyList()
        }

        override suspend fun addToFavorite(userId: String, restaurantId: String): Boolean {
            val result = dataBaseContainer.userDetailsCollection.updateOne(
                UserDetailsCollection::userId eq ObjectId(userId),
                addToSet(UserDetailsCollection::favorite, ObjectId(restaurantId))
            )
            return result.isUpdatedSuccessfully()
        }

        override suspend fun deleteFromFavorite(userId: String, restaurantId: String): Boolean {
            val result = dataBaseContainer.userDetailsCollection.updateOne(
                UserDetailsCollection::userId eq ObjectId(userId),
                pull(UserDetailsCollection::favorite, ObjectId(restaurantId))
            )
            return result.isUpdatedSuccessfully()
        }

        // endregion
    }

