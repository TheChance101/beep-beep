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

suspend inline fun <reified T> HttpClient.tryToExecuteWebSocket(
    api: APIs,
    path: String,
    attributes: Attributes
): Flow<T> {
    attributes.put(AttributeKey("API"), api.value)
    val host = System.getenv(attributes[AttributeKey("API")])
    return flow {
        webSocket(urlString = "ws://$host$path") {
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


suspend inline fun <reified T> HttpClient.tryToSendLocation(
    data: T,
    api: APIs,
    path: String,
    attributes: Attributes
) {
    attributes.put(AttributeKey("API"), api.value)
    val host = System.getenv(attributes[AttributeKey("API")])
    webSocket(urlString = "ws://$host$path") {
        sendSerialized(data)
    }
}

suspend inline fun <reified T> HttpClient.tryToSendWebSocketData(
    data: T,
    api: APIs,
    path: String,
    attributes: Attributes
) {
    attributes.put(AttributeKey("API"), api.value)
    val host = System.getenv(attributes[AttributeKey("API")])
    webSocket(urlString = "ws://$host$path") {
        sendSerialized(data)
    }
}


suspend inline fun <reified T> HttpClient.tryToSendAndReceiveWebSocketData(
    data: T,
    api: APIs,
    path: String,
    attributes: Attributes
): Flow<T> {
    attributes.put(AttributeKey("API"), api.value)
    val host = System.getenv(attributes[AttributeKey("API")])
    webSocket(urlString = "ws://$host$path") {
        try {
            sendSerialized(data)
        } catch (e: Exception) {
            throw Exception(e.message.toString())
        }
    return flow {
        webSocket(urlString = "ws://$host$path") {
            sendSerialized(data)
            emit(receiveDeserialized<T>())
        }
    }
}