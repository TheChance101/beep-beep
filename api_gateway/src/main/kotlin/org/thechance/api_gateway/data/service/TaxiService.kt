package org.thechance.api_gateway.data.service

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.util.*
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Single
import org.thechance.api_gateway.data.model.PaginationResponse
import org.thechance.api_gateway.data.model.taxi.TaxiDto
import org.thechance.api_gateway.data.model.taxi.TripDto
import org.thechance.api_gateway.data.utils.ErrorHandler
import org.thechance.api_gateway.data.utils.tryToExecute
import org.thechance.api_gateway.util.APIs

@Single
class TaxiService(
    private val client: HttpClient,
    private val attributes: Attributes,
    private val errorHandler: ErrorHandler
) {
    suspend fun getAllTaxi(languageCode: String, page: Int, limit: Int): PaginationResponse<TaxiDto> {
        return client.tryToExecute(
            api = APIs.TAXI_API,
            attributes = attributes,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(
                    errorCodes,
                    languageCode
                )
            }
        ) {
            get("/taxi") {
                parameter("page", page)
                parameter("limit", limit)
            }
        }
    }

    suspend fun getTaxiById(
        id: String,
        languageCode: String
    ): TaxiDto {
        return client.tryToExecute(
            api = APIs.TAXI_API,
            attributes = attributes,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, languageCode)
            }
        ) {
            get("/taxi/$id")
        }
    }

    @OptIn(InternalAPI::class)
    suspend fun createTaxi(
        taxiDto: TaxiDto,
        languageCode: String
    ): TaxiDto {
        return client.tryToExecute(
            api = APIs.TAXI_API,
            attributes = attributes,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, languageCode)
            }
        ) {

            post("/taxi") {
                body = Json.encodeToString(TaxiDto.serializer(), taxiDto)
            }
        }
    }

    @OptIn(InternalAPI::class)
    suspend fun editTaxi(
        id: String,
        taxiDto: TaxiDto,
        languageCode: String
    ): TaxiDto {
        return client.tryToExecute(
            api = APIs.TAXI_API,
            attributes = attributes,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, languageCode)
            }
        ) {
            put("/taxi/$id") {
                body = Json.encodeToString(TaxiDto.serializer(), taxiDto)
            }
        }
    }

    suspend fun deleteTaxi(
        id: String,
        languageCode: String
    ): TaxiDto {
        return client.tryToExecute(
            api = APIs.TAXI_API,
            attributes = attributes,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, languageCode)
            }
        ) {
            delete("/taxi/$id")
        }
    }

    @OptIn(InternalAPI::class)
    suspend fun findTaxisByQuery(
        status: Boolean,
        color: Long?,
        seats: Int?,
        query: String?,
        driverIds: List<String>,
        language: String
    ): List<TaxiDto> {
        return client.tryToExecute(
            api = APIs.TAXI_API,
            attributes = attributes,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, language)
            }
        ) {
            post("/taxis/search") {
                parameter("status", status)
                parameter("color", color)
                parameter("seats", seats)
                parameter("plate_number", query)
                body = Json.encodeToString(ListSerializer(String.serializer()), driverIds)
            }
        }
    }

    @OptIn(InternalAPI::class)
    suspend fun createTrip(trip: TripDto, languageCode: String): TripDto {
        return client.tryToExecute(
            api = APIs.TAXI_API,
            attributes = attributes,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, languageCode)
            }
        ) {
            post("/trip") {
                body = Json.encodeToString(TripDto.serializer(), trip)
            }
        }
    }

    suspend fun getTripById(tripId: String, languageCode: String): TripDto {
        return client.tryToExecute(
            api = APIs.TAXI_API,
            attributes = attributes,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, languageCode)
            },
            method = { get("/trip/$tripId") }
        )
    }

    @OptIn(InternalAPI::class)
    suspend fun approveTrip(
        taxiId: String,
        tripId: String,
        driverId: String,
        languageCode: String
    ): TripDto {
        return client.tryToExecute(
            api = APIs.TAXI_API,
            attributes = attributes,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, languageCode)
            }
        ) {
            val formData = FormDataContent(Parameters.build {
                append("tripId", tripId)
                append("taxiId", taxiId)
                append("driverId", driverId)
            })
            put("/trip/approve") {
                body = formData
            }
        }
    }

    @OptIn(InternalAPI::class)
    suspend fun finishTrip(tripId: String, driverId: String, languageCode: String): TripDto {
        return client.tryToExecute(
            api = APIs.TAXI_API,
            attributes = attributes,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, languageCode)
            }
        ) {
            val formData = FormDataContent(Parameters.build {
                append("tripId", tripId)
                append("driverId", driverId)
            })
            put("/trip/finish") {
                body = formData
            }
        }
    }

    suspend fun deleteTrip(tripId: String, languageCode: String): TripDto {
        return client.tryToExecute(
            api = APIs.TAXI_API,
            attributes = attributes,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, languageCode)
            },
            method = { delete("/trip/$tripId") }
        )
    }
}
