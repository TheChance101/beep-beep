package data.gateway.remote

import data.remote.model.BaseResponse
import domain.entity.PaginationItems
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.websocket.receiveDeserialized
import io.ktor.client.plugins.websocket.wss
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import presentation.base.NoInternetException
import presentation.base.PermissionDenied
import presentation.base.ServerSideException
import presentation.base.UnknownErrorException
import presentation.base.UserNotFoundException
import presentation.base.WrongPasswordException

abstract class BaseRemoteGateway(val client: HttpClient) {

    protected suspend inline fun <reified T> tryToExecute(
        method: HttpClient.() -> HttpResponse
    ): T {
        try {
            return client.method().body()
        } catch (e: ClientRequestException) {
            val errorMessages = e.response.body<BaseResponse<String>>().status.errorMessages
            errorMessages?.let(::throwMatchingException)
            throw UnknownErrorException(e.message)
        } catch (e: ServerSideException) {
            println("${e.message}")
            throw NoInternetException()
        } catch (e: Exception) {
            throw NoInternetException()
        }
    }

    protected suspend inline fun <reified T> HttpClient.tryToExecuteWebSocket(path: String): Flow<T> {
        return flow {
            wss(path = path) {
                while (true) {
                    try {
                        emit(receiveDeserialized<T>())
                    } catch (e: ClientRequestException) {
                        val errorMessages =
                            e.response.body<BaseResponse<String>>().status.errorMessages
                        errorMessages?.let(::throwMatchingException)
                        throw UnknownErrorException("Unknown Error")
                    } catch (e: ServerSideException) {
                        println("${e.message}")
                        throw NoInternetException()
                    } catch (e: Exception) {
                        println("${e.message}")
                        throw NoInternetException()
                    }
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    fun throwMatchingException(errorMessages: Map<String, String>) {
        println("errors: ${errorMessages.keys.toString()}")
        when {
            errorMessages.containsErrors(WRONG_PASSWORD) ->
                throw WrongPasswordException(
                    errorMessages.getOrEmpty(WRONG_PASSWORD)
                )

            errorMessages.containsErrors(USER_NOT_EXIST) ->
                throw UserNotFoundException(
                    errorMessages.getOrEmpty(USER_NOT_EXIST)
                )
            errorMessages.containsErrors(INVALID_PERMISSION) ->
                throw PermissionDenied(errorMessages.getOrEmpty(INVALID_PERMISSION))


            else -> throw UnknownErrorException("UnKnow Error")
        }
    }

    fun Map<String, String>.containsErrors(vararg errorCodes: String): Boolean =
        keys.containsAll(errorCodes.toList())

    private fun Map<String, String>.getOrEmpty(key: String): String = get(key) ?: ""

    companion object {
        private const val WRONG_PASSWORD = "1013"
        private const val USER_NOT_EXIST = "1043"
        private const val INVALID_PERMISSION = "1014"

    }
    fun <T> paginateData(result: List<T>, page: Int, total: Long): PaginationItems<T> {
        return PaginationItems(total = total, page = page, items = result)
    }
}