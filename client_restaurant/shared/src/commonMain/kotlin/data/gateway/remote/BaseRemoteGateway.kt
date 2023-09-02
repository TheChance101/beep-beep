package data.gateway.remote

import data.remote.model.BaseResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.statement.*
import presentation.base.*

abstract class BaseRemoteGateway(val client: HttpClient) {

    protected suspend inline fun <reified T> tryToExecute(
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

    fun throwMatchingException(errorMessages: Map<String, String>) {
        when {
            errorMessages.containsErrors(WRONG_PASSWORD) ->
                throw InvalidPasswordException(errorMessages.getOrEmpty(WRONG_PASSWORD))

            errorMessages.containsErrors(USER_NOT_EXIST) ->
                throw InvalidUserNameException(errorMessages.getOrEmpty(USER_NOT_EXIST))

            else -> throw UnknownErrorException()
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