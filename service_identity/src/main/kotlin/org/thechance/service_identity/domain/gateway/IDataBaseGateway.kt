package org.thechance.service_identity.domain.gateway

import org.thechance.service_identity.data.security.hashing.SaltedHash
import org.thechance.service_identity.domain.entity.*

interface IDataBaseGateway {
    // region: Permission
    suspend fun getPermission(permissionId: Int): Permission
    suspend fun addPermission(permission: CreatePermissionRequest): Boolean
    suspend fun updatePermission(permissionId: Int, permission: UpdatePermissionRequest): Boolean
    suspend fun deletePermission(permissionId: Int): Boolean
    suspend fun getListOfPermission(): List<Permission>

    // endregion: Permission

    //region address

    suspend fun addAddress(userId: String, address: CreateAddressRequest): Boolean

    suspend fun deleteAddress(id: String): Boolean

    suspend fun updateAddress(id: String, address: UpdateAddressRequest): Boolean

    suspend fun getAddress(id: String): Address

    suspend fun getUserAddresses(userId: String): List<Address>

    //endregion

    // region: user
    suspend fun getUserById(id: String): User

    suspend fun getUsers(page: Int, limit: Int, searchTerm: String = ""): List<UserManagement>

    suspend fun createUser(saltedHash: SaltedHash, user: CreateUserRequest): Boolean

    suspend fun updateUser(id: String, user: UpdateUserRequest): Boolean

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