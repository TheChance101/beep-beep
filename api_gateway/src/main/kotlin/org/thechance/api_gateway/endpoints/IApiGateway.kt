package org.thechance.api_gateway.endpoints

import org.thechance.api_gateway.data.model.TokenConfiguration
import org.thechance.api_gateway.data.model.UserManagement
import org.thechance.api_gateway.data.model.UserTokens
import org.thechance.api_gateway.data.model.identity.AddressResource
import org.thechance.api_gateway.data.model.identity.PermissionResource
import org.thechance.api_gateway.data.model.identity.UserManagementResource
import org.thechance.api_gateway.data.model.identity.UserResource
import java.util.*

interface IApiGateway {

    // region identity

    // region User
    suspend fun createUser(
        fullName: String,
        username: String,
        password: String,
        email: String,
        locale: Locale
    ): Boolean

    suspend fun loginUser(
        userName: String,
        password: String,
        tokenConfiguration: TokenConfiguration,
        locale: Locale
    ): UserTokens

    suspend fun getUsers(
        page: Int,
        limit: Int,
        searchTerm: String = "",
        locale: Locale
    ): List<UserManagementResource>

    suspend fun getUserById(id: String): UserResource

    suspend fun deleteUser(id: String, locale: Locale): Boolean

    suspend fun getUserByUsername(username: String): UserManagement

    // endregion: user

    //region Address

    suspend fun deleteAddress(id: String, locale: Locale): Boolean


    suspend fun getAddress(id: String, locale: Locale): AddressResource

    suspend fun getUserAddresses(userId: String, locale: Locale): List<AddressResource>

    //endregion

    //region Wallet
    suspend fun subtractFromWallet(userId: String, amount: Double, locale: Locale): Boolean

    suspend fun getWalletBalance(userId: String, locale: Locale): Double

    suspend fun addToWallet(userId: String, amount: Double, locale: Locale): Boolean
    //endregion

    // region Permission
    suspend fun getPermission(permissionId: Int, locale: Locale): PermissionResource

    suspend fun deletePermission(permissionId: Int, locale: Locale): Boolean
    suspend fun getListOfPermission(locale: Locale): List<PermissionResource>

    // endregion: Permission

    // region User Permission management

    suspend fun addPermissionToUser(userId: String, permissionId: Int, locale: Locale): Boolean

    suspend fun removePermissionFromUser(userId: String, permissionId: Int, locale: Locale): Boolean

    suspend fun getUserPermissions(userId: String, locale: Locale): List<PermissionResource>
    // endregion: user permission management

    // endregion identity

    suspend fun generateUserTokens(
        userId: String,
        userPermissions: List<Int>,
        tokenConfiguration: TokenConfiguration
    ): UserTokens
}