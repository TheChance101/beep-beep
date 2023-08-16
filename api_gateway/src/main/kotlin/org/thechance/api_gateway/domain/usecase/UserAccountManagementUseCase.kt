package org.thechance.api_gateway.domain.usecase

import org.koin.core.annotation.Single
import org.thechance.api_gateway.domain.entity.TokenClaim
import org.thechance.api_gateway.domain.entity.TokenConfiguration
import org.thechance.api_gateway.domain.entity.UserTokens
import org.thechance.api_gateway.domain.gateway.IApiGateway
import org.thechance.api_gateway.domain.security.ITokenService

/**
 * Created by Aziza Helmy on 8/14/2023.
 */
interface IUserAccountManagementUseCase {

    suspend fun createUser(fullName: String, username: String, password: String, email: String): Boolean
    suspend fun loginUser(
        userName: String,
        password: String,
        tokenConfiguration: TokenConfiguration,
    ): UserTokens
//    suspend fun getUser(id: String): UserManagementResource
//    suspend fun securePassword(password: String): SaltedHash
//    suspend fun deleteUser(id: String): Boolean

}

@Single
class UserAccountManagementUseCase(
    private val apiGateway: IApiGateway,
    private val userInfoValidationUseCase: IUserInfoValidationUseCase,
    private val tokenManagementService: ITokenService
) : IUserAccountManagementUseCase {
    override suspend fun createUser(fullName: String, username: String, password: String, email: String): Boolean {
        userInfoValidationUseCase.validateUserInformation(fullName, username, password, email)
        return apiGateway.createUser(fullName, username, password, email)
    }

    override suspend fun loginUser(
        userName: String,
        password: String,
        tokenConfiguration: TokenConfiguration,
    ): UserTokens {
        userInfoValidationUseCase.validateLoginInformation(userName, password)
        if (apiGateway.loginUser(userName, password)) {
            val user = apiGateway.getUserByUsername(userName)
            val userIdClaim = TokenClaim("userId", user.id)
            val userTokens = tokenManagementService.generateTokens(tokenConfiguration, userIdClaim)
            apiGateway.saveRefreshToken(user.id, userTokens.refreshToken, userTokens.refreshTokenExpirationDate)
            return userTokens
        } else
            throw Exception("Invalid username or password")
    }

//    override suspend fun refreshUserTokens(refreshToken: String){
//        if (apiGateway.validateRefreshToken(refreshToken)){
//            apiGateway.refreshUserTokens(refreshToken)
//        }else
//            throw Exception("Invalid refresh token")
//    }

//    override suspend fun getUser(id: String): UserManagementResource {
//    }
//
//    override suspend fun securePassword(password: String): SaltedHash {
//    }
//
//    override suspend fun deleteUser(id: String): Boolean {
//    }

}