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
import org.litote.kmongo.util.UpdateConfiguration.updateOnlyNotNullProperties
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
import org.thechance.service_identity.domain.util.NOT_FOUND
import org.thechance.service_identity.domain.util.USER_ALREADY_EXISTS
import org.thechance.service_identity.domain.util.USER_NOT_FOUND

@Single
class DataBaseGateway(private val dataBaseContainer: DataBaseContainer) : IDataBaseGateway {

    init {
        CoroutineScope(Dispatchers.IO).launch { createUniqueIndexIfNotExists() }
    }

    //region Address

    override suspend fun addAddress(userId: String, location: Location): Boolean {
        val address = AddressCollection(userId = ObjectId(userId), location = location.toCollection())
        dataBaseContainer.userDetailsCollection.updateOne(
            filter = UserDetailsCollection::userId eq ObjectId(userId),
            update = Updates.addToSet(UserDetailsCollection::addressIds.name, address.id)
        )
        return dataBaseContainer.addressCollection.insertOne(address).wasAcknowledged()
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

    //region Permission
    override suspend fun getPermission(permissionId: Int): Permission {
        return dataBaseContainer.permissionCollection.findOneById(permissionId)?.toEntity()
            ?: throw ResourceNotFoundException(NOT_FOUND)
    }

    override suspend fun addPermission(permission: Permission): Boolean {
        val maximumId = dataBaseContainer.permissionCollection.find().toList().size
        return dataBaseContainer.permissionCollection.insertOne(permission.toCollection(maximumId + 1))
            .wasAcknowledged()
    }

    override suspend fun deletePermission(permissionId: Int): Boolean {
        return dataBaseContainer.permissionCollection.updateOne(
            filter = Filters.and(
                PermissionCollection::id eq permissionId,
                PermissionCollection::isDeleted eq false
            ),
            update = setValue(PermissionCollection::isDeleted, true)
        ).isUpdatedSuccessfully()
    }

    override suspend fun getListOfPermission(): List<Permission> {
        return dataBaseContainer.permissionCollection.find(
            PermissionCollection::isDeleted eq false
        ).toList().toEntity()
    }

    override suspend fun updatePermission(permissionId: Int, permission: String?): Boolean {
        return dataBaseContainer.permissionCollection.updateOneById(
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
            dataBaseContainer.userCollection.createIndex(Indexes.ascending("username"), indexOptions)
        }
    }

    private suspend fun isUniqueIndexCreated(): Boolean {
        val indexName = "username_1"
        val indexInfo = dataBaseContainer.userCollection.listIndexes<Indexes>().toList()
            .filter { it.equals(indexName) }

        return indexInfo.isNotEmpty()
    }

    override suspend fun getUserById(id: String): User {
        val wallet = getWalletByUserId(id)
        val userAddresses = getUserAddresses(id)
        val userPermission = getUserPermissions(id)

        return dataBaseContainer.userCollection.aggregate<DetailedUserCollection>(
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
        return dataBaseContainer.userCollection.find(
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
        saltedHash: SaltedHash, fullName: String, username: String, email: String
    ): UserManagement {

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
            dataBaseContainer.userDetailsCollection.insertOne(UserDetailsCollection(userId = userDocument.id))
            dataBaseContainer.userCollection.insertOne(userDocument)
            return userDocument.toManagedEntity()
        } catch (exception: MongoWriteException) {
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

    override suspend fun subtractFromWallet(userId: String, amount: Double): Boolean {
        return dataBaseContainer.walletCollection.updateOne(
            filter = WalletCollection::userId eq ObjectId(userId),
            update = inc(WalletCollection::walletBalance, -amount)
        ).isUpdatedSuccessfully()
    }

    override suspend fun getWalletBalance(userId: String): Double {
        return dataBaseContainer.walletCollection.findOne(
            WalletCollection::userId eq ObjectId(userId)
        )?.walletBalance ?: throw ResourceNotFoundException(NOT_FOUND)
    }

    override suspend fun addToWallet(userId: String, amount: Double): Boolean {
        return dataBaseContainer.walletCollection.updateOne(
            filter = WalletCollection::userId eq ObjectId(userId),
            update = inc(WalletCollection::walletBalance, amount)
        ).isUpdatedSuccessfully()
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
    override suspend fun addPermissionToUser(userId: String, permissionId: Int): Boolean {
        val permission = dataBaseContainer.permissionCollection.findOne(PermissionCollection::id eq permissionId)
            ?: throw ResourceNotFoundException(NOT_FOUND)

        return dataBaseContainer.userCollection.updateOne(
            filter = UserCollection::id eq ObjectId(userId),
            update = push(UserCollection::permissions, permission)
        ).isUpdatedSuccessfully()
    }

    override suspend fun removePermissionFromUser(userId: String, permissionId: Int): Boolean {
        return dataBaseContainer.userCollection.updateOne(
            filter = UserCollection::id eq ObjectId(userId),
            update = pullByFilter(UserCollection::permissions, PermissionCollection::id eq permissionId)
        ).isUpdatedSuccessfully()
    }

    override suspend fun getUserPermissions(userId: String): List<Permission> {
        return dataBaseContainer.userCollection.findOneById(ObjectId(userId))?.permissions?.toEntity()
            ?: emptyList()
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