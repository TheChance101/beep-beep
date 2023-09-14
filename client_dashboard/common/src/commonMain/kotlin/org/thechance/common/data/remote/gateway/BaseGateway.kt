package org.thechance.common.data.remote.gateway

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.statement.*
import org.thechance.common.data.remote.model.ServerResponse
import org.thechance.common.domain.util.InvalidCredentialsException
import org.thechance.common.domain.util.NoInternetException
import org.thechance.common.domain.util.UnknownErrorException
import org.thechance.common.domain.util.UserNotFoundException
import java.net.ConnectException

abstract class BaseGateway {

    suspend inline fun <reified T> tryToExecute(
        client: HttpClient,
        method: HttpClient.() -> HttpResponse,
    ): T {
        try {
            return client.method().body()
        } catch (e: ClientRequestException) {
            val errorMessages = e.response.body<ServerResponse<String>>().status?.errorMessages
            errorMessages?.let(::throwMatchingException)
            throw UnknownErrorException()
        } catch (e: ConnectException) {
            throw NoInternetException()
        } catch (e: Exception) {
            throw UnknownErrorException()
        }
    }

    fun throwMatchingException(errorMessages: Map<String, String>) {
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

    private fun Map<String, String>.containsErrors(vararg errorCodes: String): Boolean =
        keys.containsAll(errorCodes.toList())

    private fun Map<String, String>.getOrEmpty(key: String): String = get(key) ?: ""

    companion object {
        const val WRONG_PASSWORD = "1013"
        const val USER_NOT_EXIST = "1043"
    }

}