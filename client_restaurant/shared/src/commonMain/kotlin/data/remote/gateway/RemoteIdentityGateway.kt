package data.remote.gateway

import data.remote.model.BaseResponse
import data.remote.model.UserTokensDto
import domain.gateway.IRemoteIdentityGateway
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.header
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.Parameters
import presentation.base.InternetException
import presentation.base.InvalidPasswordException
import presentation.base.InvalidUserNameException
import presentation.base.NoInternetException
import presentation.base.UnknownErrorException


class RemoteIdentityGateway(private val client: HttpClient) : IRemoteIdentityGateway {

    override suspend fun loginUser(userName: String, password: String): Pair<String, String> {
        val result = tryToExecute<BaseResponse<UserTokensDto>> {
            submitForm(
                formParameters = Parameters.build {
                    append("username", userName)
                    append("password", password)
                }
            ) {
                url("login")
                header("Accept-Language", "ar")
                header("Country-Code", "EG")
            }
        }.value

        return Pair(result?.accessToken ?: "", result?.refreshToken ?: "")
    }

    private suspend inline fun <reified T> tryToExecute(
        method: HttpClient.() -> HttpResponse
    ): T {

        try {
            return client.method().body()
        } catch (e: ClientRequestException) {
            val errorMessages = e.response.body<BaseResponse<*>>().status.errorMessages
            errorMessages?.let { throwMatchingException(it) }
            throw UnknownErrorException()
        } catch (e: InternetException) {
            throw NoInternetException()
        } catch (e: Exception) {
            throw UnknownErrorException()
        }
    }

    private fun throwMatchingException(errorMessages: Map<String, String>) {
        errorMessages.let {
            if (it.containsErrors(WRONG_PASSWORD)) {
                throw InvalidPasswordException(it.getOrEmpty(WRONG_PASSWORD))
            } else {
                if (it.containsErrors(USER_NOT_EXIST)) {
                    throw InvalidUserNameException(it.getOrEmpty(USER_NOT_EXIST))
                } else {
                    throw UnknownErrorException()
                }
            }
        }
    }

    private fun Map<String, String>.containsErrors(vararg errorCodes: String): Boolean =
        keys.containsAll(errorCodes.toList())

    private fun Map<String, String>.getOrEmpty(key: String): String = get(key) ?: ""

    companion object {
        const val WRONG_PASSWORD = "1013"
        const val USER_NOT_EXIST = "1043"
    }
}