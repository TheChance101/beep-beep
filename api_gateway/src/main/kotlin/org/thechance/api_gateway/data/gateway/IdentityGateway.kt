package org.thechance.api_gateway.data.gateway

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.util.*
import org.koin.core.annotation.Single
import org.thechance.api_gateway.data.utils.ErrorHandler
import org.thechance.api_gateway.data.mappers.toManagedUser
import org.thechance.api_gateway.data.model.*
import org.thechance.api_gateway.data.model.identity.UserManagementResource
import org.thechance.api_gateway.endpoints.gateway.IIdentityGateway
import org.thechance.api_gateway.data.model.TokenType
import org.thechance.api_gateway.data.security.ITokenService
import org.thechance.api_gateway.util.APIs
import java.util.*


@Single(binds = [IIdentityGateway::class])
class IdentityGateway(
    client: HttpClient,
    attributes: Attributes,
    private val tokenManagementService: ITokenService,
    private val errorHandler: ErrorHandler
) : BaseGateway(client = client, attributes = attributes), IIdentityGateway {
    override suspend fun createUser(
        fullName: String,
        username: String,
        password: String,
        email: String,
        locale: Locale
    ): Boolean {
        return tryToExecute<Boolean>(
            APIs.IDENTITY_API,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
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

    override suspend fun loginUser(
        userName: String,
        password: String,
        tokenConfiguration: TokenConfiguration,
        locale: Locale
    ): UserTokens {
        tryToExecute<Boolean>(
            api = APIs.IDENTITY_API,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(
                    errorCodes,
                    locale
                )
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

        return generateUserTokens(user.id, user.permissions.map { it.id }, tokenConfiguration)
    }


    override suspend fun getUsers(
        page: Int,
        limit: Int,
        searchTerm: String,
        locale: Locale
    ): List<UserManagementResource> {
        return tryToExecute<List<UserManagementResource>>(APIs.IDENTITY_API) {
            get("/users") {
                parameter("page", page)
                parameter("limit", limit)
                parameter("searchTerm", searchTerm)
            }
        }
    }

    override suspend fun getUserByUsername(username: String): UserManagement {
        return tryToExecute<UserManagementResource>(APIs.IDENTITY_API) {
            get("user/get-user") {
                parameter("username", username)
            }
        }.toManagedUser()
    }

    override suspend fun generateUserTokens(
        userId: String,
        userPermissions: List<Int>,
        tokenConfiguration: TokenConfiguration,
    ): UserTokens {

        val accessTokenExpirationDate =
            getExpirationDate(tokenConfiguration.accessTokenExpirationTimestamp)
        val refreshTokenExpirationDate =
            getExpirationDate(tokenConfiguration.refreshTokenExpirationTimestamp)

        val refreshToken =
            generateUserToken(userId, userPermissions, tokenConfiguration, TokenType.REFRESH_TOKEN)
        val accessToken =
            generateUserToken(userId, userPermissions, tokenConfiguration, TokenType.ACCESS_TOKEN)

        return UserTokens(
            accessTokenExpirationDate.time,
            refreshTokenExpirationDate.time,
            accessToken,
            refreshToken
        )
    }

    private suspend fun getExpirationDate(timestamp: Long): Date {
        return Date(System.currentTimeMillis() + timestamp)
    }

    private suspend fun generateUserToken(
        userId: String,
        userPermissions: List<Int>,
        tokenConfiguration: TokenConfiguration,
        tokenType: TokenType
    ): String {
        val userIdClaim = TokenClaim("userId", userId)
        val claims = userPermissions.map { TokenClaim("role", it.toString()) }
        val accessTokenClaim = TokenClaim("tokenType", tokenType.name)
        return tokenManagementService.generateToken(
            tokenConfiguration,
            userIdClaim,
            *claims.toTypedArray(),
            accessTokenClaim
        )
    }
}
