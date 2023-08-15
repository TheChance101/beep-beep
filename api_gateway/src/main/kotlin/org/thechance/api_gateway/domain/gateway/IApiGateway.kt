package org.thechance.api_gateway.domain.gateway

import org.thechance.api_gateway.data.model.identity.*

interface IApiGateway {

    // region identity

    // region User
    suspend fun createUser(fullName:String,username:String,password:String,email:String): Boolean
    suspend fun loginUser(userName: String, password: String): String
    suspend fun getUsers(page: Int, limit: Int, searchTerm: String = ""): List<UserManagementResource>
    suspend fun getUserById(id: String): User
    suspend fun deleteUser(id: String): Boolean

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

}