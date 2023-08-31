package org.thechance.api_gateway.data.gateway

import org.koin.core.annotation.Single
import org.thechance.api_gateway.data.model.TaxiResource

import org.thechance.api_gateway.endpoints.gateway.ITaxiGateway
import org.thechance.api_gateway.util.APIs
import java.util.Locale
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.util.*
import kotlinx.serialization.json.Json

import org.thechance.api_gateway.data.utils.ErrorHandler

import java.util.*

@Single(binds = [ITaxiGateway::class])
class TaxiGateway(
    client: HttpClient,
    attributes: Attributes,

    private val errorHandler: ErrorHandler
) : BaseGateway(client = client, attributes = attributes), ITaxiGateway {


    override suspend fun getAllTaxi(locale: Locale, page: Int, limit: Int): List<TaxiResource> {
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
    ): TaxiResource {
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
        taxi: TaxiResource,
        locale: Locale
    ): TaxiResource {
        return tryToExecute(
            api = APIs.TAXI_API,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) {

            post("/taxi") {
                body = Json.encodeToString(TaxiResource.serializer(), taxi)
            }
        }
    }

    @OptIn(InternalAPI::class)
    override suspend fun editTaxi(
        id: String,
        taxi: TaxiResource,
        locale: Locale
    ): TaxiResource {
        return tryToExecute(
            api = APIs.TAXI_API,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) {
            put("/taxi/$id") {
                body = Json.encodeToString(TaxiResource.serializer(), taxi)
            }
        }
    }

    override suspend fun deleteTaxi(
        id: String,
        locale: Locale
    ): TaxiResource {
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