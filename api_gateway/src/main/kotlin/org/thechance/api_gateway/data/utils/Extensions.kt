package org.thechance.api_gateway.data.utils

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.thechance.api_gateway.util.APIs

suspend inline fun <reified T> HttpClient.tryToExecute(
    api: APIs,
    attributes: Attributes,
    setErrorMessage: (errorCodes: List<Int>) -> Map<Int, String> = { emptyMap() },
    method: HttpClient.() -> HttpResponse
): T {
    attributes.put(AttributeKey("API"), api.value)
    val response = this.method()
    if (response.status.isSuccess()) {
        return response.body<T>()
    } else {
        val errorResponse = response.body<List<Int>>()
        val errorMessage = setErrorMessage(errorResponse)
        throw LocalizedMessageException(errorMessage)
    }
}


suspend inline fun <reified T> HttpClient.tryToExecuteFromWebSocket(api: APIs, path: String,attributes: Attributes): Flow<T> {
    attributes.put(AttributeKey("API"), api.value)
    return flow {
        this@tryToExecuteFromWebSocket.webSocket(path = path) {
            emit(receiveDeserialized<T>())
        }
    }.flowOn(Dispatchers.IO)
}