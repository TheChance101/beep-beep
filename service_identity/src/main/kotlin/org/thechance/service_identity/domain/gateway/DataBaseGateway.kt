package org.thechance.service_identity.domain.gateway

import org.thechance.service_identity.domain.entity.*

interface DataBaseGateway {
    // region: Permission
    suspend fun getPermission(permissionId: Int): Permission
    suspend fun addPermission(permission: Permission): Boolean
    suspend fun updatePermission(permissionId: Int, permission: Permission): Boolean
    suspend fun deletePermission(permissionId: Int): Boolean
    suspend fun getListOfPermission(): List<Permission>

    // endregion: Permission

    // region: wallet
    suspend fun getWallet(walletId: String): Wallet
    suspend fun createWallet(wallet: Wallet): Boolean
    suspend fun updateWallet(walletId: String, wallet: Wallet): Boolean

    // endregion: wallet

    //region address

    suspend fun addAddress(userId: String, address: Address): Boolean

    suspend fun deleteAddress(id: String): Boolean

    suspend fun updateAddress(id: String, address: Address): Boolean

    suspend fun getAddress(id: String): Address

    suspend fun getUserAddresses(userId: String): List<Address>

    //endregion

    // region: user
    suspend fun getUserById(id: String): User

    suspend fun getUsers(fullName: String = "", username: String = ""): List<ManagedUser>

    suspend fun createUser(user: User): Boolean

    suspend fun updateUser(id: String, user: User): Boolean

    suspend fun deleteUser(id: String): Boolean

    // endregion: user

    // region: user permission management

    suspend fun addPermissionToUser(userId: String, permissionId: Int): Boolean

    suspend fun removePermissionFromUser(userId: String, permissionId: Int): Boolean

    suspend fun getUserPermissions(userId: String): List<Permission>

    // endregion: user permission management
    suspend fun getWalletByUserId(userId: String): Wallet
}