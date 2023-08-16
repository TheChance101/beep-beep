package org.thechance.api_gateway.domain.gateway

import org.thechance.api_gateway.data.model.identity.AddressResource
import org.thechance.api_gateway.data.model.identity.PermissionResource
import org.thechance.api_gateway.data.model.identity.UserManagementResource
import org.thechance.api_gateway.data.model.identity.UserResource
import org.thechance.api_gateway.domain.entity.UserManagement

interface IApiGateway {

    // region identity

    // region User
    suspend fun createUser(fullName: String, username: String, password: String, email: String): Boolean
    suspend fun loginUser(userName: String, password: String): Boolean
    suspend fun getUsers(page: Int, limit: Int, searchTerm: String = ""): List<UserManagementResource>
    suspend fun getUserById(id: String): UserResource
    suspend fun deleteUser(id: String): Boolean
    suspend fun getToken(id: Long, role: String): String

    suspend fun getUserByUsername(username: String): UserManagement

    suspend fun saveRefreshToken(userId: String, refreshToken: String, expirationDate: Long): Boolean

    // endregion: user

    //region Address

    suspend fun deleteAddress(id: String): Boolean


    suspend fun getAddress(id: String): AddressResource

    suspend fun getUserAddresses(userId: String): List<AddressResource>

    //endregion

    //region Wallet
    suspend fun subtractFromWallet(userId: String, amount: Double): Boolean

    suspend fun getWalletBalance(userId: String): Double

    suspend fun addToWallet(userId: String, amount: Double): Boolean
    //endregion

    // region Permission
    suspend fun getPermission(permissionId: Int): PermissionResource

    suspend fun deletePermission(permissionId: Int): Boolean
    suspend fun getListOfPermission(): List<PermissionResource>

    // endregion: Permission

    // region User Permission management

    suspend fun addPermissionToUser(userId: String, permissionId: Int): Boolean

    suspend fun removePermissionFromUser(userId: String, permissionId: Int): Boolean

    suspend fun getUserPermissions(userId: String): List<PermissionResource>

    // endregion: user permission management

    // endregion identity

//    suspend fun refreshUserTokens(refreshToken: String): Boolean

//    suspend fun validateRefreshToken(refreshToken: String): Boolean
}