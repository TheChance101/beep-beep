package org.thechance.api_gateway.data.gateway

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.util.*
import org.koin.core.annotation.Single
import org.thechance.api_gateway.data.model.TokenClaim
import org.thechance.api_gateway.data.model.TokenConfiguration
import org.thechance.api_gateway.data.model.TokenType
import org.thechance.api_gateway.data.model.UserTokens
import org.thechance.api_gateway.data.model.identity.UserManagementResource
import org.thechance.api_gateway.data.security.ITokenService
import org.thechance.api_gateway.data.utils.ErrorHandler
import org.thechance.api_gateway.endpoints.gateway.IIdentityGateway
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
        fullName: String, username: String, password: String, email: String, locale: Locale
    ): UserManagementResource {
        return tryToExecute<UserManagementResource>(
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
        userName: String, password: String, tokenConfiguration: TokenConfiguration, locale: Locale
    ): UserTokens {
        tryToExecute<Boolean>(
            api = APIs.IDENTITY_API,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
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
        return generateUserTokens(user.id,userName, user.permission, tokenConfiguration)
    }


    override suspend fun getUsers(
        page: Int, limit: Int, searchTerm: String, locale: Locale
    ): List<UserManagementResource> {
        return tryToExecute<List<UserManagementResource>>(APIs.IDENTITY_API) {
            get("/users") {
                parameter("page", page)
                parameter("limit", limit)
                parameter("searchTerm", searchTerm)
            }
        }
    }

    override suspend fun getUserByUsername(username: String): UserManagementResource {
        return tryToExecute<UserManagementResource>(APIs.IDENTITY_API) {
            get("user/get-user") {
                parameter("username", username)
            }
        }
    }

    override suspend fun generateUserTokens(
        userId: String, username: String, userPermission: Int, tokenConfiguration: TokenConfiguration
    ): UserTokens {

        val accessTokenExpirationDate = getExpirationDate(tokenConfiguration.accessTokenExpirationTimestamp)
        val refreshTokenExpirationDate = getExpirationDate(tokenConfiguration.refreshTokenExpirationTimestamp)

        val refreshToken = generateToken(userId, username, userPermission, tokenConfiguration, TokenType.REFRESH_TOKEN)
        val accessToken = generateToken(userId, username, userPermission, tokenConfiguration, TokenType.ACCESS_TOKEN)

        return UserTokens(accessTokenExpirationDate.time, refreshTokenExpirationDate.time, accessToken, refreshToken)
    }

    override suspend fun deleteUser(userId: String, locale: Locale): Boolean {
        return tryToExecute<Boolean>(api = APIs.IDENTITY_API, setErrorMessage = { errorCodes ->
            errorHandler.getLocalizedErrorMessage(errorCodes, locale)
        }) {
            delete("/user/$userId")
        }
    }

    private fun getExpirationDate(timestamp: Long): Date {
        return Date(System.currentTimeMillis() + timestamp)
    }

    private fun generateToken(
        userId: String,username : String, userPermission: Int, tokenConfiguration: TokenConfiguration, tokenType: TokenType
    ): String {
        val userIdClaim = TokenClaim("userId", userId)
        val rolesClaim = TokenClaim("permission", userPermission.toString())
        val usernameClaim = TokenClaim("username", username)
        val accessTokenClaim = TokenClaim("tokenType", tokenType.name)
        return tokenManagementService.generateToken(tokenConfiguration, userIdClaim, usernameClaim, rolesClaim, accessTokenClaim)
    }

}
