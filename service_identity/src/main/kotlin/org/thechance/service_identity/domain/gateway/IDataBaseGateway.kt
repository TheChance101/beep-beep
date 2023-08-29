package org.thechance.service_identity.domain.gateway

import org.thechance.service_identity.domain.entity.*

interface IDataBaseGateway {

    //region address

    suspend fun addAddress(
        userId: String,
        location: Location
    ): Boolean

    suspend fun deleteAddress(id: String): Boolean

    suspend fun updateAddress(id: String, location: Location): Boolean

    suspend fun getAddress(id: String): Address

    suspend fun getUserAddresses(userId: String): List<Address>

    //endregion

    // region: user
    suspend fun getUserById(id: String): User

    suspend fun getUsers(
        page: Int,
        limit: Int,
        searchTerm: String = ""
    ): List<UserManagement>

    suspend fun createUser(
        saltedHash: SaltedHash,
        fullName: String,
        username: String,
        email: String
    ): UserManagement

    suspend fun updateUser(
        id: String,
        saltedHash: SaltedHash?,
        fullName: String?,
        username: String?,
        email: String?
    ): Boolean

    suspend fun deleteUser(id: String): Boolean

    // endregion: user

    // region token

    suspend fun getSaltedHash(username: String): SaltedHash

    // endregion

    // region: user permission management

    suspend fun addPermissionToUser(userId: String, permission: Int): Boolean

    suspend fun removePermissionFromUser(userId: String, permission: Int): Boolean

    suspend fun getUserPermission(userId: String): Int

    // endregion: user permission management
    suspend fun subtractFromWallet(userId: String, amount: Double): Boolean

    suspend fun getWalletBalance(userId: String): Double

    suspend fun addToWallet(userId: String, amount: Double): Boolean

    suspend fun getUserByUsername(username: String): UserManagement

}