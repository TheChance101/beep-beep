package org.thechance.api_gateway.data.gateway

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.util.*
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Single
import org.thechance.api_gateway.data.model.Cuisine
import org.thechance.api_gateway.data.utils.ErrorHandler
import org.thechance.api_gateway.endpoints.gateway.IDashboardGetaway
import org.thechance.api_gateway.util.APIs
import java.util.*


@Single(binds = [IDashboardGetaway::class])
class DashboardGetaway(
    client: HttpClient,
    attributes: Attributes,
    private val errorHandler: ErrorHandler
) : BaseGateway(client = client, attributes = attributes), IDashboardGetaway {
    @OptIn(InternalAPI::class)
    override suspend fun addCuisine(
        name: String, id: String, permissions: List<String>, locale: Locale
    ) = tryToExecute<Cuisine>(
        APIs.RESTAURANT_API,
        setErrorMessage = { errorCodes ->
            errorHandler.getLocalizedErrorMessage(errorCodes, locale)
        }
    ) {
        request("/cuisine") {
            method = HttpMethod.Post
            val jsonPayload =
                Json.encodeToString(Cuisine.serializer(), Cuisine(name = name))
            body = jsonPayload
        }
    }


    override suspend fun getCuisines() {
        TODO("Not yet implemented")
    }
}