package org.thechance.api_gateway.data.gateway

import org.koin.core.annotation.Single
import org.thechance.api_gateway.data.model.TaxiResource
import org.thechance.api_gateway.data.utils.LocalizedMessageException
import org.thechance.api_gateway.endpoints.gateway.ITaxiGateway
import org.thechance.api_gateway.util.APIs
import java.util.Locale
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.util.*
import org.thechance.api_gateway.data.utils.ErrorHandler

@Single(binds = [ITaxiGateway::class])
class TaxiGateway(
    client: HttpClient,
    attributes: Attributes,
    private val errorHandler: ErrorHandler
) : BaseGateway(client = client, attributes = attributes), ITaxiGateway {
    override suspend fun getAllTaxiDrivers(
        permissions: List<Int>,
        locale: Locale
    ): List<TaxiResource> {
        if (!permissions.contains(1)) {
            throw LocalizedMessageException(
                errorHandler.getLocalizedErrorMessage(
                    listOf(8000),
                    locale
                )
            )
        }

        return tryToExecute(
            api = APIs.TAXI_API,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(
                    errorCodes,
                    locale
                )
            }
        ) {
            get("/taxi")
        }
    }

    override suspend fun getTaxiDriverById(
        id: String,
        permissions: List<Int>,
        locale: Locale
    ): TaxiResource {
        return tryToExecute(
            api = APIs.TAXI_API,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(
                    errorCodes,
                    locale
                )
            }
        ) {
            get("/taxi/$id")
        }
    }

    override suspend fun createTaxiDriver(
        plateNumber: String,
        color: Int,
        type: String,
        driverId: String,
        seats: Int,
        permissions: List<Int>,
        locale: Locale
    ): TaxiResource {
        if (!permissions.contains(1)) {
            throw LocalizedMessageException(
                errorHandler.getLocalizedErrorMessage(
                    listOf(8000),
                    locale
                )
            )
        }
        return tryToExecute(
            api = APIs.TAXI_API,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(
                    errorCodes,
                    locale
                )
            }
        ) {
            submitForm("/taxi",
                formParameters = parameters {
                    append("plateNumber", plateNumber)
                    append("color", color.toString())
                    append("type", type)
                    append("driverId", driverId)
                    append("seats", seats.toString())
                }
            )
        }
    }

    override suspend fun updateTaxiDriver(
        id: String,
        plateNumber: String,
        color: Int,
        type: String,
        driverId: String,
        seats: Int,
        permissions: List<Int>,
        locale: Locale
    ): TaxiResource {
        if (!permissions.contains(1)) {
            throw LocalizedMessageException(
                errorHandler.getLocalizedErrorMessage(
                    listOf(8000),
                    locale
                )
            )
        }
        return tryToExecute(
            api = APIs.TAXI_API,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(
                    errorCodes,
                    locale
                )
            }
        ) {
            submitForm("/taxi/$id",
                formParameters = parameters {
                    append("plateNumber", plateNumber)
                    append("color", color.toString())
                    append("type", type)
                    append("driverId", driverId)
                    append("seats", seats.toString())
                }
            )
        }
    }

    override suspend fun deleteTaxiDriver(
        id: String,
        permissions: List<Int>,
        locale: Locale
    ): TaxiResource {
        if (!permissions.contains(1)) {
            throw LocalizedMessageException(
                errorHandler.getLocalizedErrorMessage(
                    listOf(8000),
                    locale
                )
            )
        }
        return tryToExecute(
            api = APIs.TAXI_API,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(
                    errorCodes,
                    locale
                )
            }
        ) {
            delete("/taxi/$id")
        }
    }
}