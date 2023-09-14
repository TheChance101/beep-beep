package data.gateway.remote

import data.remote.model.ServerResponse
import domain.utils.AuthorizationException
import domain.utils.GeneralException
import domain.utils.InternetException
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.statement.HttpResponse

abstract class BaseGateway(val client: HttpClient) {
    suspend inline fun <reified T> tryToExecute(
        method: HttpClient.() -> HttpResponse,
    ): T {
        try {
            return client.method().body()
        } catch (e: ClientRequestException) {
            //TODO: replace ServerResponse<String> with ServerResponse<T> in next deployment
            val errorMessages = e.response.body<ServerResponse<String>>().status.errorMessages
            errorMessages?.let(::throwMatchingException)
            throw GeneralException.UnknownErrorException
        } catch (e: InternetException.NoInternetException) {
            throw InternetException.NoInternetException()
        } catch (e: Exception) {
            throw GeneralException.UnknownErrorException
        }
    }

    fun throwMatchingException(errorMessages: Map<String, String>) {
        errorMessages.let {
            if (it.containsErrors(WRONG_PASSWORD)) {
                throw AuthorizationException.InvalidCredentialsException(
                    it.getOrEmpty(
                        WRONG_PASSWORD
                    )
                )
            }

            if (it.containsErrors(USER_NOT_EXIST)) {
                throw AuthorizationException.UserNotFoundException(it.getOrEmpty(USER_NOT_EXIST))
            }

            if (it.containsErrors(USER_ALREADY_EXIST)) {
                throw AuthorizationException.UserAlreadyExistException(
                    it.getOrEmpty(USER_ALREADY_EXIST)
                )
            }

            throw GeneralException.UnknownErrorException
        }
    }

    private fun Map<String, String>.containsErrors(vararg errorCodes: String): Boolean =
        keys.containsAll(errorCodes.toList())

    private fun Map<String, String>.getOrEmpty(key: String): String = get(key) ?: ""

    companion object {
        const val WRONG_PASSWORD = "1013"
        const val USER_NOT_EXIST = "1043"
        const val USER_ALREADY_EXIST = "1002"
    }
}