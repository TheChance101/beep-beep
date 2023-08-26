package data.gateway

import data.local.mapper.toEntity
import data.remote.model.BaseResponse
import data.remote.model.UserTokensDto
import domain.entity.UserTokens
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
import presentation.base.InvalidCredentialsException
import presentation.base.NoInternetException
import presentation.base.UnknownErrorException
import presentation.base.UserNotFoundException


class RemoteIdentityGateway(private val client: HttpClient) : IRemoteIdentityGateway {

    override suspend fun loginUser(userName: String, password: String): UserTokens {
        return tryToExecute<BaseResponse<UserTokensDto>>() {
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
        }.value?.toEntity() ?: throw Exception()
    }

    private suspend inline fun <reified T> tryToExecute(
        method: HttpClient.() -> HttpResponse
    ): T {

        try {
            return client.method().body<T>()
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
        if (errorMessages.containsErrors("1013")) {
            throw InvalidCredentialsException()
        } else if (errorMessages.containsErrors("1043")) {
            throw UserNotFoundException(errorMessages["1043"] ?: "")
        } else {
            throw UnknownErrorException()
        }
    }

    private fun Map<String, String>.containsErrors(vararg errorCodes: String): Boolean =
        keys.containsAll(errorCodes.toList())

}