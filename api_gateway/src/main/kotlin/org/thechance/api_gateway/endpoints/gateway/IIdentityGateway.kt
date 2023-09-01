package org.thechance.api_gateway.endpoints.gateway

import org.thechance.api_gateway.data.model.BasePaginationResponse
import org.thechance.api_gateway.data.model.User
import org.thechance.api_gateway.data.model.UserTokens
import org.thechance.api_gateway.data.security.TokenConfiguration
import java.util.*

interface IIdentityGateway {
    suspend fun createUser(
        fullName: String, username: String, password: String, email: String, locale: Locale
    ): User

    suspend fun loginUser(
        userName: String, password: String, tokenConfiguration: TokenConfiguration, locale: Locale
    ): UserTokens

    suspend fun getUsers(page: Int, limit: Int, searchTerm: String = "", locale: Locale): BasePaginationResponse<User>

    suspend fun getUserByUsername(username: String): User

    suspend fun updateUserPermission(userId: String, permission: Int): User

    suspend fun generateUserTokens(
        userId: String, username: String, userPermission: Int, tokenConfiguration: TokenConfiguration
    ): UserTokens

    suspend fun deleteUser(userId: String, locale: Locale): Boolean
}