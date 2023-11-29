package data.gateway.remote

import data.remote.model.ServerResponse
import domain.entity.PaginationItems
import domain.utils.AuthorizationException
import domain.utils.InternetException
import domain.utils.RestaurantException
import domain.utils.UnknownErrorException
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

abstract class BaseGateway(val client: HttpClient) {

    suspend inline fun <reified T> tryToExecuteWebSocket(path: String): Flow<T> {
        return flow {
            client.wss(path = path) {
                while (true) {
                    try {
                        emit(receiveDeserialized<T>())
                    } catch (e: Exception) {
                        throw Exception(e.message.toString())
                    }
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend inline fun <reified T> tryToExecute(method: HttpClient.() -> HttpResponse): T {
        try {
            return client.method().body()
        } catch (e: ClientRequestException) {
            val errorMessages = e.response.body<ServerResponse<T>>().status.errorMessages
            errorMessages?.let(::throwMatchingException)
            throw UnknownErrorException(e.message.toString())
        } catch (e: InternetException.NoInternetException) {
            throw InternetException.NoInternetException()
        } catch (e: Exception) {
            throw UnknownErrorException(e.message.toString())
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

            if (it.containsErrors(MEAL_ALREADY_EXIST)) {
                throw RestaurantException.CartIsFullException(it.getOrEmpty(MEAL_ALREADY_EXIST))
            }

            if (it.containsErrors(USER_ALREADY_EXIST)) {
                throw AuthorizationException.UserAlreadyExistException(
                    it.getOrEmpty(
                        USER_ALREADY_EXIST
                    )
                )
            }

            throw UnknownErrorException(it.getOrEmpty("unknown"))
        }
    }

    private fun Map<String, String>.containsErrors(vararg errorCodes: String): Boolean =
        keys.containsAll(errorCodes.toList())

    private fun Map<String, String>.getOrEmpty(key: String): String = get(key) ?: ""

    companion object {
        const val WRONG_PASSWORD = "1013"
        const val USER_NOT_EXIST = "1043"
        const val USER_ALREADY_EXIST = "1002"
        const val MEAL_ALREADY_EXIST = "2503"
    }

    fun <T> paginateData(result: List<T>, page: Int, total: Long): PaginationItems<T> {
        return PaginationItems(total = total, page = page, items = result)
    }
}
