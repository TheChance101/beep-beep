package org.thechance.api_gateway.data.gateway

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.util.*
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Single
import org.thechance.api_gateway.data.model.Cuisine
import org.thechance.api_gateway.data.utils.ErrorHandler
import org.thechance.api_gateway.data.utils.LocalizedMessageException
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
        name: String, permissions: List<Int>, locale: Locale
    ): Cuisine {
        //TODO()  need to change 1
        val ADMIN = 1
        return if (ADMIN in permissions) {
            tryToExecute<Cuisine>(
                APIs.RESTAURANT_API,
                setErrorMessage = { errorCodes ->
                    errorHandler.getLocalizedErrorMessage(errorCodes, locale)
                }
            ) {
                post("/cuisine") {
                    body = Json.encodeToString(Cuisine.serializer(), Cuisine(name = name))
                }
            }
        } else {
            throw LocalizedMessageException(errorHandler.getLocalizedErrorMessage(listOf(8000), locale))
        }

    }


}