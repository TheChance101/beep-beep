package org.thechance.api_gateway.data.gateway

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.util.*
import org.koin.core.annotation.Single
import org.thechance.api_gateway.data.model.TaxiResource
import org.thechance.api_gateway.data.utils.ErrorHandler
import org.thechance.api_gateway.endpoints.gateway.ITaxiGateway
import org.thechance.api_gateway.util.APIs
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
            get("/taxi"){
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

    override suspend fun createTaxi(
        plateNumber: String,
        color: Long,
        type: String,
        driverId: String,
        seats: Int,
        isAvailable: Boolean,
        locale: Locale
    ): TaxiResource {
        return tryToExecute(
            api = APIs.TAXI_API,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) {
            submitForm("/taxi",
                formParameters = parameters {
                    append("plateNumber", plateNumber)
                    append("color", color.toString())
                    append("type", type)
                    append("driverId", driverId)
                    append("isAvailable",isAvailable.toString())
                    append("seats", seats.toString())
                }
            )
        }
    }

    override suspend fun editTaxi(
        id: String,
        plateNumber: String,
        color: Long,
        type: String,
        driverId: String,
        seats: Int,
        isAvailable: Boolean,
        locale: Locale
    ): TaxiResource {
        return tryToExecute(
            api = APIs.TAXI_API,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) {
            submitForm("/taxi/$id",
                formParameters = parameters {
                    append("plateNumber", plateNumber)
                    append("color", color.toString())
                    append("type", type)
                    append("driverId", driverId)
                    append("isAvailable",isAvailable.toString())
                    append("seats", seats.toString())
                }
            )
        }
    }

    override suspend fun deleteTaxi(id: String, locale: Locale): TaxiResource {
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