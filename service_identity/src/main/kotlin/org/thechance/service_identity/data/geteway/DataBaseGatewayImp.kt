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
import org.thechance.service_identity.data.util.*
import org.thechance.service_identity.domain.entity.*
import org.thechance.service_identity.domain.gateway.DataBaseGateway
import org.thechance.service_identity.endpoints.validation.NOT_FOUND
import org.thechance.service_identity.endpoints.validation.USER_ALREADY_EXISTS

@Single
class DataBaseGatewayImp(dataBaseContainer: DataBaseContainer) : DataBaseGateway {


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

    override suspend fun addAddress(userId: String, address: Address): Boolean {
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

    override suspend fun updateAddress(id: String, address: Address): Boolean {
        return addressCollection.updateOneById(ObjectId(id), address.toUpdateRequest()).isUpdatedSuccessfully()
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

    override suspend fun addPermission(permission: Permission): Boolean {
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


    override suspend fun updatePermission(permissionId: Int, permission: Permission): Boolean {
        return permissionCollection.updateOneById(
            id = permissionId,
            update = permission.toUpdateRequest(),
            updateOnlyNotNullProperties = true
        ).wasAcknowledged()
    }
    //endregion


    //region User
    private suspend fun createUniqueIndexIfNotExists() {
        if (!isUniqueIndexCreated()) {
            val indexOptions = IndexOptions().unique(true)
            userCollection.createIndex(Indexes.ascending("username"), indexOptions)
        }
    }

    private suspend fun isUniqueIndexCreated(): Boolean {
        val indexName = "username_1"

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
                localField = USER_COLLECTION_LOCAL_PRIMARY_FIELD,
                from = USER_DETAILS_COLLECTION,
                foreignField = USER_DETAILS_LOCAL_FIELD,
                newAs = DETAILED_USER_COLLECTION
            )
        ).toList().toEntity(wallet.walletBalance, userAddresses, userPermission).firstOrNull()
            ?: throw ResourceNotFoundException(NOT_FOUND)
    }

    override suspend fun getUsers(fullName: String, username: String): List<ManagedUser> {
        return userCollection.find(
            UserCollection::fullName regex fullName,
            UserCollection::username regex username,
            UserCollection::isDeleted eq false
        ).projection(
            UserCollection::id,
            UserCollection::fullName,
            UserCollection::username,
            UserCollection::email,
            UserCollection::permissions,
        ).toList().toManagedEntity()
    }

    override suspend fun createUser(user: User): Boolean {
        val userDocument = user.toCollection()
        try {
            val wallet = WalletCollection(userId = userDocument.id.toString(), walletBalance = 0.0)
            createWallet(wallet.toEntity())
            userDetailsCollection.insertOne(user.toDetailsCollection(userDocument.id.toHexString()))
            return userCollection.insertOne(userDocument).wasAcknowledged()
        } catch (exception: MongoWriteException) {
            throw UserAlreadyExistsException(USER_ALREADY_EXISTS)
        }
    }

    override suspend fun updateUser(id: String, user: User): Boolean {
        return userCollection.updateOneById(
            ObjectId(id),
            user.toUpdateRequest(),
            updateOnlyNotNullProperties = true
        ).isUpdatedSuccessfully()
    }

    override suspend fun deleteUser(id: String): Boolean {
        return userCollection.updateOne(
            filter = UserCollection::id eq ObjectId(id),
            update = set(UserCollection::isDeleted setTo true)
        ).isUpdatedSuccessfully()
    }
    //endregion


    // region: wallet
    override suspend fun getWallet(walletId: String): Wallet {
        return walletCollection.findOneById(ObjectId(walletId))?.toEntity()
            ?: throw ResourceNotFoundException(NOT_FOUND)
    }

    override suspend fun getWalletByUserId(userId: String): Wallet {
        return walletCollection.findOne(
            WalletCollection::userId eq userId
        )?.toEntity() ?: throw ResourceNotFoundException(NOT_FOUND)
    }

    override suspend fun createWallet(wallet: Wallet): Boolean {
        userDetailsCollection.updateOne(
            filter = UserDetailsCollection::userId eq ObjectId(wallet.userId),
            update = set(UserDetailsCollection::walletCollection setTo wallet.toCollection())
        )
        return walletCollection.insertOne(wallet.toCollection()).wasAcknowledged()
    }

    override suspend fun updateWallet(walletId: String, wallet: Wallet): Boolean {
        return walletCollection.updateOneById(
            id = ObjectId(walletId),
            update = wallet.toCollection(),
        ).wasAcknowledged()
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
        const val CLIENT_PERMISSION = 1
        private const val ADMIN_PERMISSION = 2
        private const val DELIVERY_PERMISSION = 3
        private const val TAXI_DRIVER_PERMISSION = 4
        private const val RESTAURANT_OWNER_PERMISSION = 5
        private const val SUPPORT_PERMISSION = 6
    }

}