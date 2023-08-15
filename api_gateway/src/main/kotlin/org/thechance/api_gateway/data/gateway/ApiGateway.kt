package org.thechance.api_gateway.data.gateway

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.util.*
import org.koin.core.annotation.Single
import org.thechance.api_gateway.util.APIS
import org.thechance.api_gateway.data.model.identity.*
import org.thechance.api_gateway.domain.gateway.IApiGateway


@Single(binds = [IApiGateway::class])
class ApiGateway(private val client: HttpClient, private val attributes: Attributes) : IApiGateway {

    // region identity
    override suspend fun createUser( fullName: String,username: String, password: String, email: String): Boolean =
        tryToExecute<Boolean>(APIS.IDENTITY_API) {
            submitForm("/user",
                formParameters = parameters {
                    append("fullName", fullName)
                    append("username", username)
                    append("password", password)
                    append("email", email)

                }
            )
        }

    override suspend fun loginUser(userName: String, password: String): Boolean =
        tryToExecute<Boolean>(APIS.IDENTITY_API) {
            submitForm("user",
                formParameters = parameters {
                    append("username", userName)
                    append("password", password)

                }
            )
        }


    override suspend fun getUsers(page: Int, limit: Int, searchTerm: String): List<UserManagementResource> =
        tryToExecute<List<UserManagementResource>>(APIS.IDENTITY_API) {
            get("/users") {
                parameter("page", page)
                parameter("limit", limit)
                parameter("searchTerm", searchTerm)
            }
        }


    override suspend fun getUserById(id: String): User {
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
        try {
            attributes.put(AttributeKey("API"), api.value)
            val response = client.method()
            return response.body<T>()
        } catch (e: Throwable) {
            throw e
        }
    }
}
