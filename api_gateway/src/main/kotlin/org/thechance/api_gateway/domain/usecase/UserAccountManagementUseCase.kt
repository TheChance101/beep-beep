package org.thechance.api_gateway.domain.usecase

import org.koin.core.annotation.Single
import org.thechance.api_gateway.domain.entity.TokenClaim
import org.thechance.api_gateway.domain.entity.TokenConfiguration
import org.thechance.api_gateway.domain.entity.UserTokens
import org.thechance.api_gateway.domain.gateway.IApiGateway
import org.thechance.api_gateway.domain.security.ITokenService
import org.thechance.api_gateway.domain.usecase.util.tryToExecute
import java.util.*

/**
 * Created by Aziza Helmy on 8/14/2023.
 */
interface IUserAccountManagementUseCase {

    suspend fun createUser(fullName: String, username: String, password: String, email: String, locale: Locale): Boolean
    suspend fun loginUser(
        userName: String,
        password: String,
        tokenConfiguration: TokenConfiguration,
        locale: Locale,
    ): UserTokens
//    suspend fun getUser(id: String): UserManagementResource
//    suspend fun securePassword(password: String): SaltedHash
//    suspend fun deleteUser(id: String): Boolean

    suspend fun refreshAccessToken(refreshToken: String, tokenConfiguration: TokenConfiguration): UserTokens
}

@Single
class UserAccountManagementUseCase(
    private val apiGateway: IApiGateway,
    private val getLocalizedErrorMessages: IGetLocalizedErrorMessagesUseCase,
    private val tokenManagementService: ITokenService
) : IUserAccountManagementUseCase {

    override suspend fun createUser(
        fullName: String,
        username: String,
        password: String,
        email: String,
        locale: Locale
    ): Boolean {
        return tryToExecute({ getLocalizedErrorMessages.getLocalizedErrors(it, locale) })
        { apiGateway.createUser(fullName, username, password, email) }
    }


    override suspend fun loginUser(
        userName: String,
        password: String,
        tokenConfiguration: TokenConfiguration,
        locale: Locale,
    ): UserTokens {
        tryToExecute({ getLocalizedErrorMessages.getLocalizedErrors(it, locale) })
        { !apiGateway.loginUser(userName, password) }
        return generateUserTokens(userName, tokenConfiguration)
    }

    private suspend fun generateUserTokens(
        userName: String,
        tokenConfiguration: TokenConfiguration
    ): UserTokens {
        val user = apiGateway.getUserByUsername(userName)

        val refreshToken = tokenManagementService.generateRefreshToken(tokenConfiguration)

        val accessTokenExpirationDate = getExpirationDate(tokenConfiguration.accessTokenExpirationTimestamp)
        val freshTokenExpirationDate = getExpirationDate(tokenConfiguration.refreshTokenExpirationTimestamp)
        apiGateway.saveRefreshToken(user.id, refreshToken, accessTokenExpirationDate.time)

        val accessToken = generateAccessToken(user.id, tokenConfiguration)

        return UserTokens(accessTokenExpirationDate.time, freshTokenExpirationDate.time, accessToken, refreshToken)
    }

    override suspend fun refreshAccessToken(refreshToken: String, tokenConfiguration: TokenConfiguration): UserTokens {
        val expirationDate = getExpirationDate(tokenConfiguration.accessTokenExpirationTimestamp)

        if (!apiGateway.validateRefreshToken(refreshToken)) {
            throw Exception("Invalid refresh token")
        }

        val userId = apiGateway.getUserIdByRefreshToken(refreshToken)

        val accessToken = generateAccessToken(userId, tokenConfiguration)

        return UserTokens(expirationDate.time, expirationDate.time, accessToken, refreshToken)
    }

    private fun getExpirationDate(timestamp: Long): Date {
        return Date(System.currentTimeMillis() + timestamp)
    }

    private fun generateAccessToken(userId: String, tokenConfiguration: TokenConfiguration): String {
        val userIdClaim = TokenClaim("userId", userId)
        return tokenManagementService.generateAccessToken(tokenConfiguration, userIdClaim)
    }

//    override suspend fun getUser(id: String): UserManagementResource {
//    }
//
//    override suspend fun securePassword(password: String): SaltedHash {
//    }
//
//    override suspend fun deleteUser(id: String): Boolean {
//    }

}