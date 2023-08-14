package org.thechance.api_gateway.data.gateway

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.statement.*
import io.ktor.util.*
import org.koin.core.annotation.Single
import org.thechance.api_gateway.data.model.APIS
import org.thechance.api_gateway.domain.gateway.IApiGateway

@Single(binds = [IApiGateway::class])
class ApiGateway(private val client: HttpClient, private val attributes: Attributes): IApiGateway {

    // region restaurant

    // endregion

    // region taxi

    // endregion

    // region identity

    // endregion

    // region notification

    // endregion

    private suspend inline fun <reified T> tryToExecute(
        api: APIS,
        method: HttpClient.() -> HttpResponse
    ): T {
        try {
            attributes.put(AttributeKey("API"), api.value)
            val response = client.method()
            return response.body<T>()
        } catch (e: Throwable) {
            throw e
        }
    }

}