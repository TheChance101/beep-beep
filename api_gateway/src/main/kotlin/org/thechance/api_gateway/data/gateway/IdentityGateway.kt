package org.thechance.api_gateway.data.gateway

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.util.*
import org.koin.core.annotation.Single
import org.thechance.api_gateway.data.model.BasePaginationResponse
import org.thechance.api_gateway.data.model.User
import org.thechance.api_gateway.data.model.UserTokens
import org.thechance.api_gateway.data.security.ITokenService
import org.thechance.api_gateway.data.security.TokenClaim
import org.thechance.api_gateway.data.security.TokenConfiguration
import org.thechance.api_gateway.data.security.TokenType
import org.thechance.api_gateway.data.utils.ErrorHandler
import org.thechance.api_gateway.endpoints.gateway.IIdentityGateway
import org.thechance.api_gateway.util.APIs
import org.thechance.api_gateway.util.Claim.PERMISSION
import org.thechance.api_gateway.util.Claim.TOKEN_TYPE
import org.thechance.api_gateway.util.Claim.USERNAME
import org.thechance.api_gateway.util.Claim.USER_ID
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
    ): User {
        return tryToExecute<User>(
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
        return generateUserTokens(user.id, userName, user.permission, tokenConfiguration)
    }

    override suspend fun getUsers(
        page: Int, limit: Int, searchTerm: String, locale: Locale
    ) = tryToExecute<BasePaginationResponse<User>>(APIs.IDENTITY_API) {
        get("/dashboard/user") {
            parameter("page", page)
            parameter("limit", limit)
            parameter("searchTerm", searchTerm)
        }
    }

    override suspend fun getUserByUsername(username: String) = tryToExecute<User>(APIs.IDENTITY_API) {
        get("user/get-user") {
            parameter("username", username)
        }
    }

    override suspend fun updateUserPermission(userId: String, permission: Int) = tryToExecute<User>(APIs.IDENTITY_API) {
        submitForm("/dashboard/user/$userId/permission",
            formParameters = parameters {
                append("permission", "$permission")

            }
        )
    }

    override suspend fun deleteUser(userId: String, locale: Locale): Boolean {
        return tryToExecute<Boolean>(api = APIs.IDENTITY_API, setErrorMessage = { errorCodes ->
            errorHandler.getLocalizedErrorMessage(errorCodes, locale)
        }) {
            delete("/user/$userId")
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
        val userIdClaim = TokenClaim(USER_ID, userId)
        val rolesClaim = TokenClaim(PERMISSION, userPermission.toString())
        val usernameClaim = TokenClaim(USERNAME, username)
        val accessTokenClaim = TokenClaim(TOKEN_TYPE, tokenType.name)
        return tokenManagementService.generateToken(
            tokenConfiguration,
            userIdClaim,
            usernameClaim,
            rolesClaim,
            accessTokenClaim
        )
    }

}
