package org.thechance.service_identity.domain.gateway

import org.thechance.service_identity.domain.entity.Address
import org.thechance.service_identity.domain.entity.ManagedUser
import org.thechance.service_identity.domain.entity.Permission
import org.thechance.service_identity.domain.entity.User

interface DataBaseGateway {
    // region: Permission
    suspend fun getPermission(permissionId: Int): Permission
    suspend fun addPermission(permission: Permission): Boolean
    suspend fun updatePermission(permissionId: Int, permission: Permission): Boolean
    suspend fun deletePermission(permissionId: Int): Boolean
    suspend fun getListOfPermission(): List<Permission>

    // endregion: Permission

    //region address

    suspend fun addAddress(userId: String, address: Address): Boolean

    suspend fun deleteAddress(id: String): Boolean

    suspend fun updateAddress(id: String, address: Address): Boolean

    suspend fun getAddress(id: String): Address

    suspend fun getUserAddresses(userId: String): List<Address>

    //endregion

    // region: user
    suspend fun getUserById(id: String): User

    suspend fun getUsers(page: Int, limit: Int, fullName: String = "", username: String = ""): List<ManagedUser>

    suspend fun createUser(user: User): Boolean

    suspend fun updateUser(id: String, user: User): Boolean

    suspend fun deleteUser(id: String): Boolean

    // endregion: user

    // region: user permission management

    suspend fun addPermissionToUser(userId: String, permissionId: Int): Boolean

    suspend fun removePermissionFromUser(userId: String, permissionId: Int): Boolean

    suspend fun getUserPermissions(userId: String): List<Permission>

    // endregion: user permission management
    suspend fun subtractFromWallet(userId: String, amount: Double): Boolean

    suspend fun getWalletBalance(userId: String): Double

    suspend fun addToWallet(userId: String, amount: Double): Boolean

}