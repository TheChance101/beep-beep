package org.thechance.api_gateway.data.gateway

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import org.thechance.api_gateway.data.utils.LocalizedMessageException
import org.thechance.api_gateway.util.APIs

abstract class BaseGateway(
    val attributes: Attributes,
    val client: HttpClient
) {

    protected suspend inline fun <reified T> tryToExecute(
        api: APIs,
        setErrorMessage: (errorCodes: List<Int>) -> Map<Int, String> = { emptyMap() },
        method: HttpClient.() -> HttpResponse
    ): T {
        attributes.put(AttributeKey("API"), api.value)
        val response = client.method()
        if (response.status.isSuccess()) {
            return response.body<T>()
        } else {
            val errorResponse = response.body<List<Int>>()
            val errorMessage = setErrorMessage(errorResponse)
            throw LocalizedMessageException(errorMessage)
        }
    }

    protected suspend inline fun <reified T> tryToExecuteFromWebSocket(api: APIs, path: String): Flow<T> {
        attributes.put(AttributeKey("API"), api.value)
        return flow {
            client.webSocket(path = path) {
                emit(receiveDeserialized<T>())
            }
        }.flowOn(Dispatchers.IO)
    }

}