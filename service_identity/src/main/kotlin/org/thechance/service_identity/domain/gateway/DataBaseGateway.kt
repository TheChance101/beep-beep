package org.thechance.service_identity.domain.gateway

import org.thechance.service_identity.domain.entity.Address
import org.thechance.service_identity.domain.entity.Permission
import org.thechance.service_identity.domain.entity.User
import org.thechance.service_identity.domain.entity.Wallet

interface DataBaseGateway {
    suspend fun getPermission(permissionId: String): Permission?
    suspend fun addPermission(permission: Permission): Boolean
    suspend fun updatePermission(permissionId: String, permission: Permission): Boolean
    suspend fun deletePermission(permissionId: String): Boolean

    // region: wallet
    suspend fun getWallet(walletId: String): Wallet
    suspend fun createWallet(wallet: Wallet): Boolean
    suspend fun updateWallet(walletId: String, wallet: Wallet): Boolean

    // endregion: wallet

    //region address

    suspend fun addAddress(address: Address): Boolean

    suspend fun deleteAddress(id: String): Boolean

    suspend fun updateAddress(id: String, address: Address): Boolean

    suspend fun getAddress(id: String): Address?

    suspend fun getUserAddresses(userId: String): List<Address>

    //endregion

    // region: user
    suspend fun getUserById(id: String): User?

    suspend fun getUsers(): List<User>

    suspend fun createUser(user: User): Boolean

    suspend fun updateUser(id: String, user: User): Boolean

    suspend fun deleteUser(id: String): Boolean

    suspend fun addPermissionToUser(userId: String, permissionId: String)

    suspend fun removePermissionFromUser(userId: String, permissionId: String)

    suspend fun getDetailedUsers(): List<User>

    suspend fun getUserPermissions(userId: String): List<Permission>


    // endregion: user
}