package data.remote.gateway

import data.remote.model.BaseResponse
import domain.InvalidPasswordException
import domain.InvalidUserNameException
import domain.NoInternetException
import domain.SocketException
import domain.UnknownErrorException
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.websocket.receiveDeserialized
import io.ktor.client.plugins.websocket.sendSerialized
import io.ktor.client.plugins.websocket.wss
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


abstract class BaseRemoteGateway(val client: HttpClient) {

    protected suspend inline fun <reified T> tryToExecute(
        method: HttpClient.() -> HttpResponse
    ): T {
        try {
            return client.method().body()
        } catch (e: ClientRequestException) {
            val errorMessages = e.response.body<BaseResponse<*>>().status?.errorMessages
            errorMessages?.let { throwMatchingException(it) }
            throw UnknownErrorException(e.message)
        } catch (e: UnresolvedAddressException) {
            throw NoInternetException()
        } catch (e: Exception) {
            throw UnknownErrorException(e.message.toString())
        }
    }
    suspend inline fun <reified T> HttpClient.tryToExecuteWebSocket(path: String): Flow<T> {
        return flow {
            wss(path = path) {
                while (true) {
                    try {
                        emit(receiveDeserialized<T>())
                    } catch (e: Exception) {
                        throw SocketException(e.message.orEmpty())
                    }
                }
            }
        }.flowOn(Dispatchers.IO)
    }
    suspend inline fun <reified T> HttpClient.tryToSendWebSocketData(
        data: T,
        path: String,
    ) {
        wss(path) {
            try {
                sendSerialized(data)
            } catch (e: Exception) {
                throw SocketException(e.message.orEmpty())
            }
        }
    }

    fun throwMatchingException(errorMessages: Map<String, String>) {
        when {
            errorMessages.containsErrors(WRONG_PASSWORD) ->
                throw InvalidPasswordException(errorMessages.getOrEmpty(WRONG_PASSWORD))

            errorMessages.containsErrors(USER_NOT_EXIST) ->
                throw InvalidUserNameException(errorMessages.getOrEmpty(USER_NOT_EXIST))

            else -> throw UnknownErrorException("UnKnow Error")
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