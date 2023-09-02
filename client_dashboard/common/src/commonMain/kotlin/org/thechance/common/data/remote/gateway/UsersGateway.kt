package org.thechance.common.data.remote.gateway

import io.ktor.client.HttpClient
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.header
import io.ktor.client.request.url
import io.ktor.http.Parameters
import org.thechance.common.data.remote.gateway.BaseGateway
import org.thechance.common.data.remote.model.ServerResponse
import org.thechance.common.data.remote.model.UserTokensRemoteDto
import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.User
import org.thechance.common.domain.getway.IUsersGateway

class UsersGateway(private val client: HttpClient) : BaseGateway(), IUsersGateway {
    override suspend fun getUserData(): String = "aaaa"

    override suspend fun getUsers(page: Int, numberOfUsers: Int): DataWrapper<User> {
        TODO("Not yet implemented")
    }

    override suspend fun loginUser(username: String, password: String): Pair<String, String> {
        val result = tryToExecute<ServerResponse<UserTokensRemoteDto>>(client) {
            submitForm(
                formParameters = Parameters.build {
                    append("username", username)
                    append("password", password)
                }
            ) {
                url("login")
                //TODO left until complete get user preferences
                header("Accept-Language", "ar")
                header("Country-Code", "EG")
            }
        }.value

        return Pair(result?.accessToken ?: "", result?.refreshToken ?: "")
    }
}