package org.thechance.api_gateway.endpoints.gateway

import org.thechance.api_gateway.data.model.TokenConfiguration
import org.thechance.api_gateway.data.model.UserTokens
import org.thechance.api_gateway.data.model.identity.UserManagementResource
import java.util.*

interface IIdentityGateway {
    suspend fun createUser(
        fullName: String, username: String, password: String, email: String, locale: Locale
    ): UserManagementResource

    suspend fun loginUser(
        userName: String, password: String, tokenConfiguration: TokenConfiguration, locale: Locale
    ): UserTokens

    suspend fun getUsers(
        page: Int, limit: Int, searchTerm: String = "", locale: Locale
    ): List<UserManagementResource>

    suspend fun getUserByUsername(username: String): UserManagementResource

    suspend fun generateUserTokens(
        userId: String, userPermission : Int, tokenConfiguration: TokenConfiguration
    ): UserTokens
}