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
import org.thechance.api_gateway.data.model.identity.AddressResource
import org.thechance.api_gateway.data.model.identity.PermissionResource
import org.thechance.api_gateway.data.model.identity.UserManagementResource
import org.thechance.api_gateway.data.model.identity.UserResource
import org.thechance.api_gateway.domain.entity.MultiErrorException
import org.thechance.api_gateway.domain.entity.UserManagement
import org.thechance.api_gateway.domain.gateway.IApiGateway
import org.thechance.api_gateway.util.APIS


@Single(binds = [IApiGateway::class])
class ApiGateway(private val client: HttpClient, private val attributes: Attributes) : IApiGateway {

    // region identity
    override suspend fun createUser(fullName: String, username: String, password: String, email: String): Boolean {
        return tryToExecute<Boolean>(APIS.IDENTITY_API) {
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

    override suspend fun loginUser(userName: String, password: String): Boolean {
        return tryToExecute<Boolean>(APIS.IDENTITY_API) {
            submitForm("/user/login",
                formParameters = parameters {
                    append("username", userName)
                    append("password", password)
                }
            )
        }
    }


    override suspend fun getUsers(page: Int, limit: Int, searchTerm: String): List<UserManagementResource> {
        return tryToExecute<List<UserManagementResource>>(APIS.IDENTITY_API) {
            get("/users") {
                parameter("page", page)
                parameter("limit", limit)
                parameter("searchTerm", searchTerm)
            }
        }
    }

    override suspend fun getUserByUsername(username: String): UserManagement {
        return tryToExecute<UserManagementResource>(APIS.IDENTITY_API) {
            get("user/get-user") {
                parameter("username", username)
            }
        }.toManagedUser()
    }

    override suspend fun saveRefreshToken(userId: String, refreshToken: String, expirationDate: Long): Boolean {
        return tryToExecute<Boolean>(APIS.IDENTITY_API) {
            submitForm("user/update-refresh-token",
                formParameters = parameters {
                    append("userId", userId)
                    append("refreshToken", refreshToken)
                    append("expirationDate", expirationDate.toString())
                }
            )
        }
    }

    override suspend fun validateRefreshToken(refreshToken: String): Boolean {
        return tryToExecute<Boolean>(APIS.IDENTITY_API) {
            submitForm("user/validate-refresh-token",
                formParameters = parameters {
                    append("refreshToken", refreshToken)
                }
            )
        }
    }

    override suspend fun getUserIdByRefreshToken(refreshToken: String): String {
        return tryToExecute<String>(APIS.IDENTITY_API) {
            submitForm("user/get-user-id-by-refresh-token",
                formParameters = parameters {
                    append("refreshToken", refreshToken)
                }
            )
        }
    }

    override suspend fun getUserById(id: String): UserResource {
        TODO("Not yet implemented")
    }


    override suspend fun deleteUser(id: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getToken(id: Long, role: String): String {
        TODO("Not yet implemented")
    }


    override suspend fun deleteAddress(id: String): Boolean {
        TODO("Not yet implemented")
    }


    override suspend fun getAddress(id: String): AddressResource {
        TODO("Not yet implemented")
    }

    override suspend fun getUserAddresses(userId: String): List<AddressResource> {
        TODO("Not yet implemented")
    }

    override suspend fun subtractFromWallet(userId: String, amount: Double): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getWalletBalance(userId: String): Double {
        TODO("Not yet implemented")
    }

    override suspend fun addToWallet(userId: String, amount: Double): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getPermission(permissionId: Int): PermissionResource {
        TODO("Not yet implemented")
    }

    override suspend fun deletePermission(permissionId: Int): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getListOfPermission(): List<PermissionResource> {
        TODO("Not yet implemented")
    }

    override suspend fun addPermissionToUser(userId: String, permissionId: Int): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun removePermissionFromUser(userId: String, permissionId: Int): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getUserPermissions(userId: String): List<PermissionResource> {
        TODO("Not yet implemented")
    }

    // endregion

    private suspend inline fun <reified T> tryToExecute(
        api: APIS,
        method: HttpClient.() -> HttpResponse
    ): T {
        attributes.put(AttributeKey("API"), api.value)
        val response = client.method()
        if (response.status.isSuccess()) {
            return response.body<T>()
        } else {
            val errorResponse = response.body<List<Int>>()
            throw MultiErrorException(errorResponse)
        }
    }
}
