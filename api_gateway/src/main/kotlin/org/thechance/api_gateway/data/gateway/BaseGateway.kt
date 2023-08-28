package org.thechance.api_gateway.data.gateway

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import io.ktor.util.AttributeKey
import io.ktor.util.Attributes
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


}