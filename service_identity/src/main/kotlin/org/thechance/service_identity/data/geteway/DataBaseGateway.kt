package org.thechance.service_identity.data.geteway

import com.mongodb.MongoWriteException
import com.mongodb.client.model.Filters
import com.mongodb.client.model.IndexOptions
import com.mongodb.client.model.Indexes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.annotation.Single
import org.litote.kmongo.*
import org.litote.kmongo.coroutine.aggregate
import org.thechance.service_identity.data.DataBaseContainer
import org.thechance.service_identity.data.collection.*
import org.thechance.service_identity.data.mappers.toCollection
import org.thechance.service_identity.data.mappers.toEntity
import org.thechance.service_identity.data.mappers.toManagedEntity
import org.thechance.service_identity.data.util.USER_DETAILS_COLLECTION
import org.thechance.service_identity.data.util.isUpdatedSuccessfully
import org.thechance.service_identity.data.util.paginate
import org.thechance.service_identity.domain.entity.*
import org.thechance.service_identity.domain.gateway.IDataBaseGateway
import org.thechance.service_identity.domain.util.INVALID_CREDENTIALS
import org.thechance.service_identity.domain.util.NOT_FOUND
import org.thechance.service_identity.domain.util.USER_ALREADY_EXISTS
import org.thechance.service_identity.domain.util.USER_NOT_FOUND
import java.util.*

@Single
class DataBaseGateway(dataBaseContainer: DataBaseContainer) :
    IDataBaseGateway {


    private val addressCollection by lazy {
        dataBaseContainer.database.getCollection<AddressCollection>(ADDRESS_COLLECTION_NAME)
    }
    private val userDetailsCollection by lazy {
        dataBaseContainer.database.getCollection<UserDetailsCollection>(USER_DETAILS_COLLECTION)
    }
    private val permissionCollection by lazy {
        dataBaseContainer.database.getCollection<PermissionCollection>(PERMISSION_COLLECTION_NAME)
    }
    private val userCollection by lazy {
        dataBaseContainer.database.getCollection<UserCollection>(USER_COLLECTION)
    }
    private val walletCollection by lazy {
        dataBaseContainer.database.getCollection<WalletCollection>(WALLET_COLLECTION)
    }

    init {
        CoroutineScope(Dispatchers.IO).launch {
            createUniqueIndexIfNotExists()
        }
    }

    //region Address

    override suspend fun addAddress(userId: String, location: Location): Boolean {
        val address = AddressCollection(userId = UUID.fromString(userId), location = location.toCollection())
        userDetailsCollection.updateOne(
            filter = UserDetailsCollection::userId eq UUID.fromString(userId),
            update = push(
                UserDetailsCollection::addresses,
                address.id.toString()
            )
        )
        return addressCollection.insertOne(address).wasAcknowledged()
    }

    override suspend fun deleteAddress(id: String): Boolean {
        userDetailsCollection.updateOne(
            filter = UserDetailsCollection::addresses contains id,
            update = pull(UserDetailsCollection::addresses, id)
        )
        return addressCollection.updateOne(
            filter = Filters.and(AddressCollection::id eq UUID.fromString(id), AddressCollection::isDeleted eq false),
            update = setValue(AddressCollection::isDeleted, true)
        ).isUpdatedSuccessfully()
    }

    override suspend fun updateAddress(id: String, location: Location): Boolean {
        return addressCollection.updateOneById(
            UUID.fromString(id),
            location,
            updateOnlyNotNullProperties = true
        ).isUpdatedSuccessfully()
    }

    override suspend fun getAddress(id: String): Address {
        return addressCollection.findOne(
            AddressCollection::id eq UUID.fromString(id),
            AddressCollection::isDeleted eq false
        )?.toEntity() ?: throw ResourceNotFoundException(NOT_FOUND)
    }

    override suspend fun getUserAddresses(userId: String): List<Address> {
        return addressCollection.find(
            AddressCollection::userId eq UUID.fromString(userId),
            AddressCollection::isDeleted eq false
        ).toList().toEntity()
    }

    //endregion

    //region Permission
    override suspend fun getPermission(permissionId: Int): Permission {
        return permissionCollection.findOneById(permissionId)?.toEntity()
            ?: throw ResourceNotFoundException(NOT_FOUND)
    }

    override suspend fun addPermission(permission: Permission): Boolean {
        val maximumId = permissionCollection.find().toList().size
        return permissionCollection.insertOne(permission.toCollection(maximumId + 1)).wasAcknowledged()
    }

    override suspend fun deletePermission(permissionId: Int): Boolean {
        return permissionCollection.updateOne(
            filter = Filters.and(
                PermissionCollection::id eq permissionId,
                PermissionCollection::isDeleted eq false
            ),
            update = setValue(PermissionCollection::isDeleted, true)
        ).isUpdatedSuccessfully()
    }

    override suspend fun getListOfPermission(): List<Permission> {
        return permissionCollection.find(
            PermissionCollection::isDeleted eq false
        ).toList().toEntity()
    }


    override suspend fun updatePermission(permissionId: Int, permission: String?): Boolean {
        return permissionCollection.updateOneById(
            id = permissionId,
            update = permission!!,
            updateOnlyNotNullProperties = true
        ).isUpdatedSuccessfully()
    }
    //endregion


    //region User
    private suspend fun createUniqueIndexIfNotExists() {
        if (!isUniqueIndexCreated()) {
            val indexOptions = IndexOptions().unique(true)
            userCollection.createIndex(Indexes.ascending(USER_NAME), indexOptions)
        }
    }

    private suspend fun isUniqueIndexCreated(): Boolean {
        val indexName = INDEX_NAME

        val indexInfo = userCollection.listIndexes<Indexes>().toList()
            .filter { it.equals(indexName) }

        return indexInfo.isNotEmpty()
    }

    override suspend fun getUserById(id: String): User {

        val wallet = getWalletByUserId(id)
        val userAddresses = getUserAddresses(id)
        val userPermission = getUserPermissions(id)

        return userCollection.aggregate<DetailedUserCollection>(
            match(
                UserCollection::id eq UUID.fromString(id),
                UserCollection::isDeleted eq false
            ),
            lookup(
                localField = UserCollection::id.name,
                from = USER_DETAILS_COLLECTION,
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
        return userCollection.find(
            searchQuery,
            UserCollection::isDeleted eq false
        ).projection(
            UserCollection::id,
            UserCollection::fullName,
            UserCollection::username,
            UserCollection::email,
            UserCollection::permissions,
        ).paginate(page, limit).toList().toManagedEntity()
    }

    override suspend fun createUser(
        saltedHash: SaltedHash,
        fullName: String,
        username: String,
        email: String
    ): Boolean {

        val userDocument = UserCollection(
            hashedPassword = saltedHash.hash,
            salt = saltedHash.salt,
            username = username,
            fullName = fullName,
            email = email
        )

        try {
            val wallet = WalletCollection(userId = userDocument.id)
            createWallet(wallet)
            userDetailsCollection.insertOne(UserDetailsCollection(userId = userDocument.id))
            return userCollection.insertOne(userDocument).wasAcknowledged()

        } catch (exception: MongoWriteException) {
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
            return userCollection.updateOneById(
                UUID.fromString(id),
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
        return userCollection.updateOne(
            filter = UserCollection::id eq UUID.fromString(id),
            update = set(UserCollection::isDeleted setTo true)
        ).isUpdatedSuccessfully()
    }

    override suspend fun getUserByUsername(username: String): UserManagement {
        return userCollection.findOne(
            UserCollection::username eq username,
            UserCollection::isDeleted eq false
        )?.toManagedEntity() ?: throw ResourceNotFoundException(NOT_FOUND)
    }

    //endregion

    // region: wallet


    private suspend fun getWalletByUserId(userId: String): WalletCollection {
        return walletCollection.findOne(
            WalletCollection::userId eq UUID.fromString(userId)
        ) ?: throw ResourceNotFoundException(NOT_FOUND)
    }

    override suspend fun subtractFromWallet(userId: String, amount: Double): Boolean {
        return walletCollection.updateOne(
            filter = WalletCollection::userId eq UUID.fromString(userId),
            update = inc(WalletCollection::walletBalance, -amount)
        ).isUpdatedSuccessfully()
    }

    override suspend fun getWalletBalance(userId: String): Double {
        return walletCollection.findOne(
            WalletCollection::userId eq UUID.fromString(userId)
        )?.walletBalance ?: throw ResourceNotFoundException(NOT_FOUND)
    }

    override suspend fun addToWallet(userId: String, amount: Double): Boolean {
        return walletCollection.updateOne(
            filter = WalletCollection::userId eq UUID.fromString(userId),
            update = inc(WalletCollection::walletBalance, amount)
        ).isUpdatedSuccessfully()
    }

    private suspend fun createWallet(wallet: WalletCollection): Boolean {
        userDetailsCollection.updateOne(
            filter = UserDetailsCollection::userId eq wallet.userId,
            update = set(UserDetailsCollection::walletCollection setTo wallet)
        )
        return walletCollection.insertOne(wallet).wasAcknowledged()
    }

    // endregion: wallet

    // region: user permission management

    override suspend fun addPermissionToUser(userId: String, permissionId: Int): Boolean {
        val permission = permissionCollection.findOne(PermissionCollection::id eq permissionId)
            ?: throw ResourceNotFoundException(NOT_FOUND)

        return userCollection.updateOne(
            filter = UserCollection::id eq UUID.fromString(userId),
            update = push(UserCollection::permissions, permission)
        ).isUpdatedSuccessfully()
    }

    override suspend fun removePermissionFromUser(userId: String, permissionId: Int): Boolean {
        return userCollection.updateOne(
            filter = UserCollection::id eq UUID.fromString(userId),
            update = pullByFilter(UserCollection::permissions, PermissionCollection::id eq permissionId)
        ).isUpdatedSuccessfully()
    }

    override suspend fun getUserPermissions(userId: String): List<Permission> {
        return userCollection.findOneById(UUID.fromString(userId))?.permissions?.toEntity()
            ?: emptyList()
    }


    // endregion: user permission management

    // region Token

    override suspend fun getSaltedHash(username: String): SaltedHash {
        val user = userCollection.findOne(
            UserCollection::username eq username
        ) ?: throw ResourceNotFoundException(USER_NOT_FOUND)
        return SaltedHash(user.hashedPassword!!, user.salt!!)
    }

    // endregion
    companion object {
        private const val WALLET_COLLECTION = "wallet"
        private const val ADDRESS_COLLECTION_NAME = "address"
        private const val PERMISSION_COLLECTION_NAME = "permission"
        private const val TOKENS_COLLECTION = "tokens"
        private const val USER_COLLECTION = "user"
        private const val USER_NAME = "username"
        private const val INDEX_NAME = "username_1"
    }

}