package org.thechance.service_identity.data.geteway

import com.mongodb.MongoWriteException
import com.mongodb.client.model.Filters
import com.mongodb.client.model.IndexOptions
import com.mongodb.client.model.Indexes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.bson.types.ObjectId
import org.koin.core.annotation.Single
import org.litote.kmongo.*
import org.litote.kmongo.coroutine.aggregate
import org.thechance.service_identity.data.DataBaseContainer
import org.thechance.service_identity.data.collection.*
import org.thechance.service_identity.data.mappers.*
import org.thechance.service_identity.data.security.hashing.SaltedHash
import org.thechance.service_identity.data.util.USER_DETAILS_COLLECTION
import org.thechance.service_identity.data.util.isUpdatedSuccessfully
import org.thechance.service_identity.data.util.paginate
import org.thechance.service_identity.domain.entity.*
import org.thechance.service_identity.domain.gateway.IDataBaseGateway
import org.thechance.service_identity.domain.util.NOT_FOUND
import org.thechance.service_identity.domain.util.USER_ALREADY_EXISTS

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

    override suspend fun addAddress(userId: String, address: CreateAddressRequest): Boolean {
        val newAddressCollection = address.toCollection(userId)
        userDetailsCollection.updateOne(
            filter = UserDetailsCollection::userId eq ObjectId(userId),
            update = push(UserDetailsCollection::addresses, newAddressCollection.id)
        )
        return addressCollection.insertOne(newAddressCollection).wasAcknowledged()
    }

    override suspend fun deleteAddress(id: String): Boolean {
        userDetailsCollection.updateOne(
            filter = UserDetailsCollection::addresses contains ObjectId(id),
            update = pull(UserDetailsCollection::addresses, ObjectId(id))
        )
        return addressCollection.updateOne(
            filter = Filters.and(AddressCollection::id eq ObjectId(id), AddressCollection::isDeleted eq false),
            update = setValue(AddressCollection::isDeleted, true)
        ).isUpdatedSuccessfully()
    }

    override suspend fun updateAddress(id: String, address: UpdateAddressRequest): Boolean {
        return addressCollection.updateOneById(
            ObjectId(id),
            address.toUpdateDocument(),
            updateOnlyNotNullProperties = true
        ).isUpdatedSuccessfully()
    }

    override suspend fun getAddress(id: String): Address {
        return addressCollection.findOne(
            AddressCollection::id eq ObjectId(id),
            AddressCollection::isDeleted eq false
        )?.toEntity() ?: throw ResourceNotFoundException(NOT_FOUND)
    }

    override suspend fun getUserAddresses(userId: String): List<Address> {
        return addressCollection.find(
            AddressCollection::userId eq ObjectId(userId),
            AddressCollection::isDeleted eq false
        ).toList().toEntity()
    }

    //endregion

    //region Permission
    override suspend fun getPermission(permissionId: Int): Permission {
        return permissionCollection.findOneById(permissionId)?.toEntity()
            ?: throw ResourceNotFoundException(NOT_FOUND)
    }

    override suspend fun addPermission(permission: CreatePermissionRequest): Boolean {
        val maximumId = permissionCollection.find().toList().size
        return permissionCollection.insertOne(permission.toCollection(maximumId + 1)).wasAcknowledged()
    }

    override suspend fun deletePermission(permissionId: Int): Boolean {
        return permissionCollection.updateOne(
            filter = Filters.and(
                PermissionCollection::_id eq permissionId,
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


    override suspend fun updatePermission(permissionId: Int, permission: UpdatePermissionRequest): Boolean {
        return permissionCollection.updateOneById(
            id = permissionId,
            update = permission.toUpdateDocument(),
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
                UserCollection::id eq ObjectId(id),
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

    override suspend fun createUser(saltedHash: SaltedHash, user: CreateUserRequest): Boolean {

        val userDocument = UserCollection(
            password = saltedHash.hash,
            salt = saltedHash.salt,
            username = user.username,
            fullName = user.fullName,
            email = user.email
        )

        try {
            val wallet = WalletCollection(userId = userDocument.id.toString())
            createWallet(wallet)

            userDetailsCollection.insertOne(UserDetailsCollection(userId = userDocument.id))
            return userCollection.insertOne(userDocument).wasAcknowledged()

        } catch (exception: MongoWriteException) {
            throw UserAlreadyExistsException(USER_ALREADY_EXISTS)
        }
    }

    override suspend fun updateUser(id: String, user: UpdateUserRequest): Boolean {
        try {
            return userCollection.updateOneById(
                ObjectId(id),
                user.toUpdateRequest(),
                updateOnlyNotNullProperties = true
            ).isUpdatedSuccessfully()
        } catch (exception: MongoWriteException) {
            throw UserAlreadyExistsException(USER_ALREADY_EXISTS)
        }
    }

    override suspend fun deleteUser(id: String): Boolean {
        return userCollection.updateOne(
            filter = UserCollection::id eq ObjectId(id),
            update = set(UserCollection::isDeleted setTo true)
        ).isUpdatedSuccessfully()
    }
    //endregion


    // region: wallet

    private suspend fun getWalletByUserId(userId: String): WalletCollection {
        return walletCollection.findOne(
            WalletCollection::userId eq userId
        ) ?: throw ResourceNotFoundException(NOT_FOUND)
    }

    override suspend fun subtractFromWallet(userId: String, amount: Double): Boolean {
        return walletCollection.updateOne(
            filter = WalletCollection::userId eq userId,
            update = inc(WalletCollection::walletBalance, -amount)
        ).isUpdatedSuccessfully()
    }

    override suspend fun getWalletBalance(userId: String): Double {
        return walletCollection.findOne(
            WalletCollection::userId eq userId
        )?.walletBalance ?: throw ResourceNotFoundException(NOT_FOUND)
    }

    override suspend fun addToWallet(userId: String, amount: Double): Boolean {
        return walletCollection.updateOne(
            filter = WalletCollection::userId eq userId,
            update = inc(WalletCollection::walletBalance, amount)
        ).isUpdatedSuccessfully()
    }

    private suspend fun createWallet(wallet: WalletCollection): Boolean {
        userDetailsCollection.updateOne(
            filter = UserDetailsCollection::userId eq ObjectId(wallet.userId),
            update = set(UserDetailsCollection::walletCollection setTo wallet)
        )
        return walletCollection.insertOne(wallet).wasAcknowledged()
    }
    // endregion: wallet

    // region: user permission management

    override suspend fun addPermissionToUser(userId: String, permissionId: Int): Boolean {
        val permission = permissionCollection.findOne(PermissionCollection::_id eq permissionId)
            ?: throw ResourceNotFoundException(NOT_FOUND)

        return userCollection.updateOne(
            filter = UserCollection::id eq ObjectId(userId),
            update = push(UserCollection::permissions, permission)
        ).isUpdatedSuccessfully()
    }

    override suspend fun removePermissionFromUser(userId: String, permissionId: Int): Boolean {
        return userCollection.updateOne(
            filter = UserCollection::id eq ObjectId(userId),
            update = pullByFilter(UserCollection::permissions, PermissionCollection::_id eq permissionId)
        ).isUpdatedSuccessfully()
    }

    override suspend fun getUserPermissions(userId: String): List<Permission> {
        return userCollection.findOneById(ObjectId(userId))?.permissions?.toEntity()
            ?: throw ResourceNotFoundException(NOT_FOUND)
    }

    // endregion: user permission management

    companion object {
        private const val WALLET_COLLECTION = "wallet"
        private const val ADDRESS_COLLECTION_NAME = "address"
        private const val PERMISSION_COLLECTION_NAME = "permission"
        private const val USER_COLLECTION = "user"
        private const val USER_NAME = "username"
        private const val INDEX_NAME = "username_1"
    }

}