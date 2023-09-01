package org.thechance.api_gateway.data.gateway

import org.koin.core.annotation.Single
import org.thechance.api_gateway.data.model.Taxi

import org.thechance.api_gateway.endpoints.gateway.ITaxiGateway
import org.thechance.api_gateway.util.APIs
import java.util.Locale
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.util.*
import kotlinx.serialization.json.Json
import org.thechance.api_gateway.data.model.BasePaginationResponse

import org.thechance.api_gateway.data.utils.ErrorHandler

@Single(binds = [ITaxiGateway::class])
class TaxiGateway(
    client: HttpClient,
    attributes: Attributes,
    private val errorHandler: ErrorHandler
) : BaseGateway(client = client, attributes = attributes), ITaxiGateway {


    override suspend fun getAllTaxi(locale: Locale, page: Int, limit: Int): BasePaginationResponse<Taxi> {
        return tryToExecute(
            api = APIs.TAXI_API,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(
                    errorCodes,
                    locale
                )
            }
        ) {
            get("/taxi") {
                parameter("page", page)
                parameter("limit", limit)
            }
        }
    }

    override suspend fun getTaxiById(
        id: String,
        locale: Locale
    ): Taxi {
        return tryToExecute(
            api = APIs.TAXI_API,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) {
            get("/taxi/$id")
        }
    }

    @OptIn(InternalAPI::class)
    override suspend fun createTaxi(
        taxi: Taxi,
        locale: Locale
    ): Taxi {
        return tryToExecute(
            api = APIs.TAXI_API,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) {

            post("/taxi") {
                body = Json.encodeToString(Taxi.serializer(), taxi)
            }
        }
    }

    @OptIn(InternalAPI::class)
    override suspend fun editTaxi(
        id: String,
        taxi: Taxi,
        locale: Locale
    ): Taxi {
        return tryToExecute(
            api = APIs.TAXI_API,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) {
            put("/taxi/$id") {
                body = Json.encodeToString(Taxi.serializer(), taxi)
            }
        }
    }

    override suspend fun deleteTaxi(
        id: String,
        locale: Locale
    ): Taxi {
        return tryToExecute(
            api = APIs.TAXI_API,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) {
            delete("/taxi/$id")
        }
    }
}