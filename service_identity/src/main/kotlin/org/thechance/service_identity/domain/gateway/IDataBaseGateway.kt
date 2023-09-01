package org.thechance.service_identity.domain.gateway

import org.thechance.service_identity.domain.entity.*
import org.thechance.service_identity.domain.security.SaltedHash

interface IDataBaseGateway {

    //region address
    suspend fun addLocation(userId: String, location: Location): Address

    suspend fun addAddress(userId: String, address: Address): Address

    suspend fun deleteAddress(id: String): Boolean

    suspend fun updateAddress(addressId: String, location: Location): Address

    suspend fun getAddress(id: String): Address

    suspend fun getUserAddresses(userId: String): List<Address>

    //endregion

    // region: user
    suspend fun getUserById(id: String): User

    suspend fun getUsers(page: Int, limit: Int, searchTerm: String = ""): List<UserManagement>

    suspend fun createUser(saltedHash: SaltedHash, fullName: String, username: String, email: String): UserManagement

    suspend fun updateUser(
        id: String, saltedHash: SaltedHash?, fullName: String?, username: String?, email: String?
    ): Boolean

    suspend fun deleteUser(id: String): Boolean

    suspend fun getNumberOfUsers(): Long

    // endregion: user

    // region token

    suspend fun getSaltedHash(username: String): SaltedHash

    // endregion

    // region: user permission management

    suspend fun updatePermissionToUser(userId: String, permission: Int): UserManagement

    suspend fun getUserPermission(userId: String): Int

    // endregion: user permission management
    suspend fun subtractFromWallet(userId: String, amount: Double): Wallet

    suspend fun getWalletBalance(userId: String): Wallet

    suspend fun addToWallet(userId: String, amount: Double): Wallet

    suspend fun getUserByUsername(username: String): UserManagement

}