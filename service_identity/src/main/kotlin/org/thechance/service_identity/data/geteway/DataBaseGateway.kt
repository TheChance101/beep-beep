package org.thechance.service_identity.data.geteway

import com.mongodb.MongoWriteException
import com.mongodb.client.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.bson.types.ObjectId
import org.koin.core.annotation.Single
import org.litote.kmongo.*
import org.litote.kmongo.coroutine.aggregate
import org.thechance.service_identity.data.DataBaseContainer
import org.thechance.service_identity.data.collection.*
import org.thechance.service_identity.data.collection.mappers.toCollection
import org.thechance.service_identity.data.collection.mappers.toEntity
import org.thechance.service_identity.data.collection.mappers.toManagedEntity
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
            userId = ObjectId(userId),
            location = location.toCollection()
        )
        dataBaseContainer.userDetailsCollection.updateOne(
            filter = UserDetailsCollection::userId eq ObjectId(userId),
            update = Updates.addToSet(UserDetailsCollection::addressIds.name, address.id)
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
            location = address.location.toCollection()
        )
        dataBaseContainer.userDetailsCollection.updateOne(
            filter = UserDetailsCollection::userId eq ObjectId(userId),
            update = Updates.addToSet(UserDetailsCollection::addressIds.name, address.id)
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
            dataBaseContainer.userCollection.createIndex(Indexes.ascending(DataBaseContainer.USER_NAME), indexOptions)
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

        return dataBaseContainer.userCollection.aggregate<DetailedUserCollection>(
            match(
                UserCollection::id eq ObjectId(id),
                UserCollection::isDeleted eq false
            ),
            lookup(
                localField = UserCollection::id.name,
                from = DataBaseContainer.USER_DETAILS_COLLECTION,
                foreignField = UserDetailsCollection::userId.name,
                newAs = DetailedUserCollection::details.name
            )
        ).toList().toEntity(wallet.walletBalance, userAddresses, userPermission).firstOrNull()
            ?: throw ResourceNotFoundException(NOT_FOUND)
    }

    override suspend fun getUsers(page: Int, limit: Int, searchTerm: String): List<UserManagement> {
        val searchQuery = or(
            UserCollection::fullName regex searchTerm,
            UserCollection::username regex searchTerm
        )
        return dataBaseContainer.userCollection.find(
            searchQuery,
            UserCollection::isDeleted eq false
        ).projection(
            UserCollection::id,
            UserCollection::fullName,
            UserCollection::username,
            UserCollection::email,
            UserCollection::permission,
        ).paginate(page, limit).toList().toManagedEntity()
    }

    override suspend fun createUser(
        saltedHash: SaltedHash, fullName: String, username: String, email: String
    ): UserManagement {
        val userNameExist = dataBaseContainer.userCollection.findOne(UserCollection::username eq username)
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
        id: String, saltedHash: SaltedHash?, fullName: String?, username: String?, email: String?
    ): Boolean {

        try {
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

    override suspend fun deleteUser(id: String): Boolean {
        return dataBaseContainer.userCollection.updateOne(
            filter = UserCollection::id eq ObjectId(id),
            update = set(UserCollection::isDeleted setTo true)
        ).isUpdatedSuccessfully()
    }

    override suspend fun getNumberOfUsers(): Long {
        return dataBaseContainer.userCollection.countDocuments(UserCollection::isDeleted eq false)
    }

    override suspend fun getUserByUsername(username: String): UserManagement {
        return dataBaseContainer.userCollection.findOne(
            UserCollection::username eq username,
            UserCollection::isDeleted eq false
        )?.toManagedEntity() ?: throw ResourceNotFoundException(NOT_FOUND)
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

    private suspend fun createWallet(wallet: WalletCollection): Boolean {
        dataBaseContainer.userDetailsCollection.updateOne(
            filter = UserDetailsCollection::userId eq wallet.userId,
            update = set(UserDetailsCollection::walletCollection setTo wallet)
        )
        return dataBaseContainer.walletCollection.insertOne(wallet).wasAcknowledged()
    }

    // endregion: wallet

    // region: user permission management

    override suspend fun updatePermissionToUser(userId: String, permission: Int): UserManagement {
        return dataBaseContainer.userCollection.findOneAndUpdate(
            filter = UserCollection::id eq ObjectId(userId),
            update = Updates.set(UserCollection::permission.name, permission),
            options = FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER)
        )?.toManagedEntity() ?: throw ResourceNotFoundException(USER_NOT_FOUND)
    }

    override suspend fun getUserPermission(userId: String): Int {
        return dataBaseContainer.userCollection.findOneById(ObjectId(userId))?.permission ?: 1
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

}