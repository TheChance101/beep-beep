package org.thechance.api_gateway.data.gateway

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.util.*
import org.koin.core.annotation.Single
import org.thechance.api_gateway.data.mappers.toManagedUser
import org.thechance.api_gateway.data.model.*
import org.thechance.api_gateway.data.model.identity.AddressResource
import org.thechance.api_gateway.data.model.identity.PermissionResource
import org.thechance.api_gateway.data.model.identity.UserManagementResource
import org.thechance.api_gateway.data.model.identity.UserResource
import org.thechance.api_gateway.endpoints.IApiGateway
import org.thechance.api_gateway.util.APIS
import java.util.*


@Single(binds = [IApiGateway::class])
class ApiGateway(
    private val client: HttpClient,
    private val attributes: Attributes,
    private val resourcesGateway: IResourcesGateway,
    private val tokenManagementService: ITokenService
) : IApiGateway {


    // region identity
    override suspend fun createUser(
        fullName: String,
        username: String,
        password: String,
        email: String,
        locale: Locale
    ): Boolean {
        return tryToExecute<Boolean>(APIS.IDENTITY_API, locale) {
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
        tryToExecute<Boolean>(APIS.IDENTITY_API, locale) {
            submitForm("/user/login",
                formParameters = parameters {
                    append("username", userName)
                    append("password", password)
                }
            )
        }
        return generateUserTokens(userName, tokenConfiguration, locale)

    }


    override suspend fun getUsers(
        page: Int,
        limit: Int,
        searchTerm: String,
        locale: Locale
    ): List<UserManagementResource> {
        return tryToExecute<List<UserManagementResource>>(APIS.IDENTITY_API, locale) {
            get("/users") {
                parameter("page", page)
                parameter("limit", limit)
                parameter("searchTerm", searchTerm)
            }
        }
    }

    override suspend fun getUserByUsername(username: String, locale: Locale): UserManagement {
        return tryToExecute<UserManagementResource>(APIS.IDENTITY_API, locale) {
            get("user/get-user") {
                parameter("username", username)
            }
        }.toManagedUser()
    }

    override suspend fun saveRefreshToken(
        userId: String,
        refreshToken: String,
        expirationDate: Long,
        locale: Locale
    ): Boolean {
        return tryToExecute<Boolean>(APIS.IDENTITY_API, locale) {
            submitForm("user/update-refresh-token",
                formParameters = parameters {
                    append("userId", userId)
                    append("refreshToken", refreshToken)
                    append("expirationDate", expirationDate.toString())
                }
            )
        }
    }

    override suspend fun validateRefreshToken(refreshToken: String, locale: Locale): Boolean {
        return tryToExecute<Boolean>(APIS.IDENTITY_API, locale) {
            submitForm("user/validate-refresh-token",
                formParameters = parameters {
                    append("refreshToken", refreshToken)
                }
            )
        }
    }

    override suspend fun getUserIdByRefreshToken(refreshToken: String, locale: Locale): String {
        return tryToExecute<String>(APIS.IDENTITY_API, locale) {
            submitForm("user/get-user-id-by-refresh-token",
                formParameters = parameters {
                    append("refreshToken", refreshToken)
                }
            )
        }
    }


    override suspend fun getUserById(id: String, locale: Locale): UserResource {
        TODO("Not yet implemented")
    }


    override suspend fun deleteUser(id: String, locale: Locale): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getToken(id: Long, role: String, locale: Locale): String {
        TODO("Not yet implemented")
    }


    override suspend fun deleteAddress(id: String, locale: Locale): Boolean {
        TODO("Not yet implemented")
    }


    override suspend fun getAddress(id: String, locale: Locale): AddressResource {
        TODO("Not yet implemented")
    }

    override suspend fun getUserAddresses(userId: String, locale: Locale): List<AddressResource> {
        TODO("Not yet implemented")
    }

    override suspend fun subtractFromWallet(userId: String, amount: Double, locale: Locale): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getWalletBalance(userId: String, locale: Locale): Double {
        TODO("Not yet implemented")
    }

    override suspend fun addToWallet(userId: String, amount: Double, locale: Locale): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getPermission(permissionId: Int, locale: Locale): PermissionResource {
        TODO("Not yet implemented")
    }

    override suspend fun deletePermission(permissionId: Int, locale: Locale): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getListOfPermission(locale: Locale): List<PermissionResource> {
        TODO("Not yet implemented")
    }

    override suspend fun addPermissionToUser(userId: String, permissionId: Int, locale: Locale): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun removePermissionFromUser(userId: String, permissionId: Int, locale: Locale): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getUserPermissions(userId: String, locale: Locale): List<PermissionResource> {
        TODO("Not yet implemented")
    }

    // endregion

    private suspend fun generateUserTokens(
        userName: String,
        tokenConfiguration: TokenConfiguration,
        locale: Locale
    ): UserTokens {
        val user = getUserByUsername(userName, locale)

        val refreshToken = tokenManagementService.generateRefreshToken(tokenConfiguration)

        val accessTokenExpirationDate = getExpirationDate(tokenConfiguration.accessTokenExpirationTimestamp)
        val freshTokenExpirationDate = getExpirationDate(tokenConfiguration.refreshTokenExpirationTimestamp)
        saveRefreshToken(user.id, refreshToken, accessTokenExpirationDate.time, locale)

        val accessToken = generateAccessToken(user.id, tokenConfiguration)

        return UserTokens(accessTokenExpirationDate.time, freshTokenExpirationDate.time, accessToken, refreshToken)
    }

    override suspend fun refreshAccessToken(
        refreshToken: String,
        tokenConfiguration: TokenConfiguration,
        locale: Locale
    ): UserTokens {
        val expirationDate = getExpirationDate(tokenConfiguration.accessTokenExpirationTimestamp)

        if (!validateRefreshToken(refreshToken, locale)) {
            throw Exception("Invalid refresh token")
        }

        val userId = getUserIdByRefreshToken(refreshToken, locale)

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

    private suspend inline fun <reified T> tryToExecute(
        api: APIS,
        locale: Locale,
        method: HttpClient.() -> HttpResponse
    ): T {
        attributes.put(AttributeKey("API"), api.value)
        val response = client.method()
        if (response.status.isSuccess()) {
            return response.body<T>()
        } else {
            val errorResponse = response.body<List<Int>>()
            val errorMessages = errorResponse.map {
                resourcesGateway.getLocalizedErrorMessage(it, locale = locale)
            }
            throw MultiLocalizedMessageException(errorMessages)
        }
    }
}
