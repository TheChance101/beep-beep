package org.thechance.common.data.remote.gateway

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.header
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.Parameters
import org.thechance.common.data.remote.model.ServerResponse
import org.thechance.common.data.remote.model.UserTokensRemoteDto
import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.InvalidCredentialsException
import org.thechance.common.domain.entity.NoInternetException
import org.thechance.common.domain.entity.UnknownErrorException
import org.thechance.common.domain.entity.User
import org.thechance.common.domain.entity.UserNotFoundException
import org.thechance.common.domain.getway.IUsersGateway
import java.net.ConnectException

class UsersGateway(private val client: HttpClient) : IUsersGateway {
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

    private suspend inline fun <reified T> tryToExecute(
        client: HttpClient,
        method: HttpClient.() -> HttpResponse,
    ): T {
        try {
            return client.method().body()
        } catch (e: ClientRequestException) {
            val errorMessages = e.response.body<ServerResponse<*>>().status.errorMessages
            errorMessages?.let { throwMatchingException(it) }
            throw UnknownErrorException()
        } catch (e: ConnectException) {
            throw NoInternetException()
        } catch (e: Exception) {
            throw UnknownErrorException()
        }
    }

    private fun throwMatchingException(errorMessages: Map<String, String>) {
        errorMessages.let {
            if (it.containsErrors(WRONG_PASSWORD)) {
                throw InvalidCredentialsException(it.getOrEmpty(WRONG_PASSWORD))
            } else {
                if (it.containsErrors(USER_NOT_EXIST)) {
                    throw UserNotFoundException(it.getOrEmpty(USER_NOT_EXIST))
                } else {
                    throw UnknownErrorException()
                }
            }
        }
    }

    companion object {
        const val WRONG_PASSWORD = "1013"
        const val USER_NOT_EXIST = "1043"
    }
}