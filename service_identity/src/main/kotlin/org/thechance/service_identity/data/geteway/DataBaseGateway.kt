package org.thechance.service_identity.data.geteway

import com.mongodb.MongoWriteException
import com.mongodb.client.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.bson.conversions.Bson
import org.bson.types.ObjectId
import org.koin.core.annotation.Single
import org.litote.kmongo.*
import org.litote.kmongo.coroutine.aggregate
import org.thechance.service_identity.data.DataBaseContainer
import org.thechance.service_identity.data.DataBaseContainer.Companion.ADDRESS_COLLECTION_NAME
import org.thechance.service_identity.data.DataBaseContainer.Companion.WALLET_COLLECTION
import org.thechance.service_identity.data.collection.*
import org.thechance.service_identity.data.collection.mappers.toCollection
import org.thechance.service_identity.data.collection.mappers.toEntity
import org.thechance.service_identity.data.collection.mappers.toManagedEntity
import org.thechance.service_identity.data.util.getNonEmptyFieldsMap
import org.thechance.service_identity.data.util.isUpdatedSuccessfully
import org.thechance.service_identity.data.util.paginate
import org.thechance.service_identity.domain.entity.*
import org.thechance.service_identity.domain.gateway.IDataBaseGateway
import org.thechance.service_identity.domain.security.SaltedHash
import org.thechance.service_identity.domain.util.*

@Single
class DataBaseGateway(private val dataBaseContainer: DataBaseContainer) : IDataBaseGateway {

    init {
        CoroutineScope(Dispatchers.IO).launch { createUniqueIndexIfNotExists() }
    }

    //region Address
    override suspend fun addLocation(userId: String, location: Location): Address {
        val address = AddressCollection(
            userId = ObjectId(userId), location = location.toCollection()
        )
        dataBaseContainer.userCollection.updateOne(
            filter = UserCollection::id eq ObjectId(userId),
            update = Updates.addToSet(UserCollection::addressIds.name, address.id)
        )
        return if (dataBaseContainer.addressCollection.insertOne(address).wasAcknowledged()) {
            address.toEntity()
        } else {
            throw ResourceNotFoundException(ERROR_IN_DB)
        }
    }

    override suspend fun addAddress(userId: String, address: Address): Address {
        val addressCollection = AddressCollection(
            userId = ObjectId(userId),
            address = address.address,
            location = address.location?.toCollection()
        )
        dataBaseContainer.userCollection.updateOne(
            filter = UserCollection::id eq ObjectId(userId),
            update = Updates.addToSet(UserCollection::addressIds.name, addressCollection.id)
        )
        return if (dataBaseContainer.addressCollection.insertOne(addressCollection).wasAcknowledged()) {
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
        return dataBaseContainer.userCollection.aggregate<DetailedUser>(
            match(
                and(
                    UserCollection::id eq ObjectId(id),
                    UserCollection::isDeleted eq false
                )
            ),
            lookup(
                from = WALLET_COLLECTION,
                localField = UserCollection::walletId.name,
                foreignField = "_id",
                newAs = "wallet"
            ),
            unwind("\$wallet"),
            lookup(
                from = ADDRESS_COLLECTION_NAME,
                localField = UserCollection::addressIds.name,
                foreignField = "_id",
                newAs = "addresses"
            ),
        ).toList().firstOrNull()?.toEntity() ?: throw UserAlreadyExistsException(USER_NOT_FOUND)
    }


    override suspend fun getUsers(options: UserOptions): List<UserManagement> {
        val searchQuery = or(
            options.query?.let { UserCollection::fullName regex it },
            options.query?.let { UserCollection::username regex it }
        )

        val orConditions = options.permissions?.map { permission ->
            or(
                UserCollection::permission eq permission,
                UserCollection::permission.bitsAllSet(permission.toLong())
            )
        }

        return dataBaseContainer.userCollection.find(
            and(
                searchQuery,
                orConditions?.let { or(*orConditions.toTypedArray()) },
                UserCollection::isDeleted eq false,
            )
        ).paginate(options.page, options.limit).toList().toManagedEntity()
    }

    override suspend fun createUser(saltedHash: SaltedHash, country: String, user: UserInfo): UserManagement {
        val userNameExist =
            dataBaseContainer.userCollection.findOne(UserCollection::username eq user.username)
        if (userNameExist == null) {
            val userDocument = user.toCollection(hash = saltedHash.hash, salt = saltedHash.salt, country)
            dataBaseContainer.userCollection.insertOne(userDocument)
            return userDocument.toManagedEntity()
        } else {
            throw UserAlreadyExistsException(USER_ALREADY_EXISTS)
        }
    }

    override suspend fun updateUser(userId: String, fullName: String?, phone: String?): UserManagement {
        try {
            val fieldsToUpdate = mutableListOf<Bson>()
            fullName?.let { fieldsToUpdate.add(Updates.set(UserCollection::fullName.name, it)) }
            phone?.let { fieldsToUpdate.add(Updates.set(UserCollection::phone.name, it)) }
            return dataBaseContainer.userCollection.findOneAndUpdate(
                filter = UserCollection::id eq ObjectId(userId),
                update = Updates.combine(fieldsToUpdate),
                options = FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER)
            )?.toManagedEntity() ?: throw UserAlreadyExistsException(NOT_FOUND)
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

    override suspend fun createWallet(userId: String, currency: String): Wallet {
        val wallet = WalletCollection(userId = ObjectId(userId), currency = currency)
        dataBaseContainer.userCollection.updateOne(
            filter = UserCollection::id eq wallet.userId,
            update = set(UserCollection::walletId setTo wallet.id)
        )
        dataBaseContainer.walletCollection.insertOne(wallet)
        return wallet.toEntity()
    }

    // endregion: wallet

    // region: user permission management

    override suspend fun updateUserPermission(userId: String, permissions: Int): UserManagement {

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
        val user = dataBaseContainer.userCollection.findOne(
            UserCollection::id eq ObjectId(userId)
        )
        return user?.favorite?.map(ObjectId::toString) ?: emptyList()
    }

    override suspend fun addToFavorite(userId: String, restaurantId: String): Boolean {
        val result = dataBaseContainer.userCollection.updateOne(
            UserCollection::id eq ObjectId(userId),
            addToSet(UserCollection::favorite, ObjectId(restaurantId))
        )
        return result.isUpdatedSuccessfully()
    }

    override suspend fun deleteFromFavorite(userId: String, restaurantId: String): Boolean {
        val result = dataBaseContainer.userCollection.updateOne(
            UserCollection::id eq ObjectId(userId),
            pull(UserCollection::favorite, ObjectId(restaurantId))
        )
        return result.isUpdatedSuccessfully()
    }

    // endregion
}




