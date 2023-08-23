package org.thechance.api_gateway.endpoints

import org.thechance.api_gateway.data.model.TokenConfiguration
import org.thechance.api_gateway.data.model.UserManagement
import org.thechance.api_gateway.data.model.UserTokens
import org.thechance.api_gateway.data.model.identity.UserManagementResource
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

    suspend fun getUserByUsername(username: String): UserManagement

    // endregion: user



    suspend fun generateUserTokens(
        userId: String,
        userPermissions: List<Int>,
        tokenConfiguration: TokenConfiguration
    ): UserTokens
}