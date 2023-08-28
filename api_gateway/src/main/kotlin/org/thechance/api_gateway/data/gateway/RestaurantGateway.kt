package org.thechance.api_gateway.data.gateway

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.util.*
import org.koin.core.annotation.Single
import org.thechance.api_gateway.data.model.restaurant.RestaurantResource
import org.thechance.api_gateway.data.utils.ErrorHandler
import org.thechance.api_gateway.data.utils.LocalizedMessageException
import org.thechance.api_gateway.endpoints.gateway.IRestaurantGateway
import org.thechance.api_gateway.endpoints.model.RestaurantRequestPermission
import org.thechance.api_gateway.util.APIs
import java.util.*

@Single(binds = [IRestaurantGateway::class])
class RestaurantGateway(
    client: HttpClient,
    attributes: Attributes,
    private val errorHandler: ErrorHandler
) : BaseGateway(client = client, attributes = attributes), IRestaurantGateway {
    override suspend fun createRequestPermission(
        restaurantName: String,
        ownerEmail: String,
        cause: String,
        locale: Locale
    ): RestaurantRequestPermission {
        return tryToExecute(
            api = APIs.RESTAURANT_API,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(
                    errorCodes,
                    locale
                )
            }
        ) {
            submitForm("/restaurant-permission-request",
                formParameters = parameters {
                    append("restaurantName", restaurantName)
                    append("ownerEmail", ownerEmail)
                    append("cause", cause)
                }
            )
        }
    }

    override suspend fun getAllRequestPermission(permissions: List<Int>, locale: Locale): List<RestaurantRequestPermission> {
        // todo: implement check permissions logic correctly
        if (!permissions.contains(1)) {
            throw LocalizedMessageException(errorHandler.getLocalizedErrorMessage(listOf(8000), locale))
        }

        return tryToExecute(
            api = APIs.RESTAURANT_API,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(
                    errorCodes,
                    locale
                )
            }
        ) {
            get("/restaurant-permission-request")
        }
    }

    override suspend fun getRestaurantInfo(locale: Locale, id: String): RestaurantResource {
        return tryToExecute<RestaurantResource>(
            APIs.RESTAURANT_API,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes,  locale)
            }
        ) {
            get("/restaurant/$id")
        }
    }


}