package org.thechance.common.data.remote.gateway

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import org.thechance.common.data.remote.mapper.toEntity
import org.thechance.common.data.remote.model.ServerResponse
import org.thechance.common.data.remote.model.UserDto
import org.thechance.common.data.remote.model.UserResponse
import org.thechance.common.data.remote.model.UserTokensRemoteDto
import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.Permission
import org.thechance.common.domain.entity.User
import org.thechance.common.domain.getway.IUsersGateway

class UsersGateway(private val client: HttpClient) : BaseGateway(), IUsersGateway {

    override suspend fun getUserData(): String = "aaaa"

    override suspend fun getUsers(
        query: String?, byPermissions: List<Permission>, byCountries: List<String>, page: Int, numberOfUsers: Int
    ): DataWrapper<User> {
        getLastRegisteredUsers(4)
        val result = tryToExecute<ServerResponse<UserResponse>>(client) {
            get(urlString = "/users") {
                parameter("page", page)
                parameter("limit", numberOfUsers)
            }
        }.value

        return DataWrapper(
            totalPages = result?.total?.div(numberOfUsers) ?: 0,
            numberOfResult = result?.total ?: 0,
            result = result?.users?.toEntity() ?: emptyList()
        )
    }

    override suspend fun loginUser(username: String, password: String): Pair<String, String> {
        val result = tryToExecute<ServerResponse<UserTokensRemoteDto>>(client) {
            submitForm(formParameters = Parameters.build {
                append("username", username)
                append("password", password)
            }) {
                url("/login")
                header("Accept-Language", "ar")
                header("Country-Code", "EG")
                header("Application-Id", "3000")
            }
        }.value

        return Pair(result?.accessToken ?: "", result?.refreshToken ?: "")
    }

    override suspend fun deleteUser(id: String): Boolean {
        return tryToExecute<ServerResponse<Boolean>>(client) {
            delete(urlString = "/user") { url { appendPathSegments(id) } }
        }.value ?: false
    }

    override suspend fun getLastRegisteredUsers(limit: Int): List<User> {
        return tryToExecute<ServerResponse<List<UserDto>>>(client) {
            get(urlString = "/user/last-register?limit=4") {
                parameter("limit", limit)
            }
        }.value?.toEntity() ?: throw UnknownError()
    }

}