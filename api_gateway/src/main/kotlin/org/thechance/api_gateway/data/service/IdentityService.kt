package org.thechance.api_gateway.data.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.util.*
import org.koin.core.annotation.Single
import org.thechance.api_gateway.data.model.PaginationResponse
import org.thechance.api_gateway.data.model.UserDto
import org.thechance.api_gateway.data.model.UserTokensResponse
import org.thechance.api_gateway.data.model.authenticate.TokenConfiguration
import org.thechance.api_gateway.data.model.authenticate.TokenType
import org.thechance.api_gateway.data.utils.ErrorHandler
import org.thechance.api_gateway.data.utils.tryToExecute
import org.thechance.api_gateway.util.APIs
import org.thechance.api_gateway.util.Claim.PERMISSION
import org.thechance.api_gateway.util.Claim.TOKEN_TYPE
import org.thechance.api_gateway.util.Claim.USERNAME
import org.thechance.api_gateway.util.Claim.USER_ID
import java.util.*


@Single
class IdentityService(
    private val client: HttpClient,
    private val attributes: Attributes,
    private val errorHandler: ErrorHandler
) {

    suspend fun createUser(
        fullName: String, username: String, password: String, email: String, languageCode: String
    ): UserDto {
        return client.tryToExecute<UserDto>(
            APIs.IDENTITY_API,
            attributes = attributes,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, languageCode)
            }
        ) {
            submitForm("/user",
                formParameters = parameters {
                    append("fullName", fullName)
                    append("username", username)
                    append("password", password)
                    append("email", email)
                }
            )
        }
    }

    suspend fun loginUser(
        userName: String, password: String, tokenConfiguration: TokenConfiguration, languageCode: String
    ): UserTokensResponse {
        client.tryToExecute<Boolean>(
            api = APIs.IDENTITY_API,
            attributes = attributes,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, languageCode)
            }
        ) {
            submitForm("/user/login",
                formParameters = parameters {
                    append("username", userName)
                    append("password", password)
                }
            )
        }
        val user = getUserByUsername(userName)
        return generateUserTokens(user.id, userName, user.permission, tokenConfiguration)
    }

    suspend fun getUsers(
        page: Int, limit: Int, searchTerm: String, languageCode: String
    ) = client.tryToExecute<PaginationResponse<UserDto>>(
        APIs.IDENTITY_API, attributes = attributes, setErrorMessage = { errorCodes ->
            errorHandler.getLocalizedErrorMessage(errorCodes, languageCode)
        }
    ) {
        get("/dashboard/user") {
            parameter("page", page)
            parameter("limit", limit)
            parameter("searchTerm", searchTerm)
        }
    }

    private suspend fun getUserByUsername(username: String) = client.tryToExecute<UserDto>(
        APIs.IDENTITY_API, attributes = attributes,
    ) {
        get("user/get-user") {
            parameter("username", username)
        }
    }

    suspend fun updateUserPermission(userId: String, permission: Int) =
        client.tryToExecute<UserDto>(
            APIs.IDENTITY_API, attributes = attributes,
        ) {
            submitForm("/dashboard/user/$userId/permission",
                formParameters = parameters {
                    append("permission", "$permission")

                }
            )
        }

    suspend fun deleteUser(userId: String, languageCode: String): Boolean {
        return client.tryToExecute<Boolean>(
            api = APIs.IDENTITY_API,
            attributes = attributes,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, languageCode)
            }) {
            delete("/user/$userId")
        }
    }

    fun generateUserTokens(
        userId: String, username: String, userPermission: Int, tokenConfiguration: TokenConfiguration
    ): UserTokensResponse {

        val accessTokenExpirationDate = getExpirationDate(tokenConfiguration.accessTokenExpirationTimestamp)
        val refreshTokenExpirationDate = getExpirationDate(tokenConfiguration.refreshTokenExpirationTimestamp)

        val refreshToken = generateToken(userId, username, userPermission, tokenConfiguration, TokenType.REFRESH_TOKEN)
        val accessToken = generateToken(userId, username, userPermission, tokenConfiguration, TokenType.ACCESS_TOKEN)

        return UserTokensResponse(
            accessTokenExpirationDate.time,
            refreshTokenExpirationDate.time,
            accessToken,
            refreshToken
        )
    }

    private fun getExpirationDate(timestamp: Long): Date {
        return Date(System.currentTimeMillis() + timestamp)
    }

    private fun generateToken(
        userId: String,
        username: String,
        userPermission: Int,
        tokenConfiguration: TokenConfiguration,
        tokenType: TokenType
    ): String {
        val accessToken = JWT.create()
            .withIssuer(tokenConfiguration.issuer)
            .withAudience(tokenConfiguration.audience)
            .withExpiresAt(Date(System.currentTimeMillis() + tokenConfiguration.accessTokenExpirationTimestamp))
            .withClaim(USER_ID, userId)
            .withClaim(PERMISSION, userPermission.toString())
            .withClaim(USERNAME, username)
            .withClaim(TOKEN_TYPE, tokenType.name)

        return accessToken.sign(Algorithm.HMAC256(tokenConfiguration.secret))
    }

}
