package org.thechance.api_gateway.data.service

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.util.*
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Single
import org.thechance.api_gateway.data.model.PaginationResponse
import org.thechance.api_gateway.data.model.taxi.TaxiDto
import org.thechance.api_gateway.data.model.taxi.TripDto
import org.thechance.api_gateway.data.utils.ErrorHandler
import org.thechance.api_gateway.data.utils.tryToExecute
import org.thechance.api_gateway.data.utils.tryToExecuteWebSocket
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
            setErrorMessage = { errorCodes -> errorHandler.getLocalizedErrorMessage(errorCodes, languageCode) },
        ) {
            get("/taxi") {
                parameter("page", page)
                parameter("limit", limit)
            }
        }
    }

    suspend fun getTaxiById(id: String, languageCode: String): TaxiDto {
        return client.tryToExecute(
            api = APIs.TAXI_API,
            attributes = attributes,
            setErrorMessage = { errorCodes -> errorHandler.getLocalizedErrorMessage(errorCodes, languageCode) },
            method = { get("/taxi/$id") }
        )
    }

    suspend fun getTaxisByDriverId(driverId: String, languageCode: String): List<TaxiDto> {
        return client.tryToExecute(
            api = APIs.TAXI_API,
            attributes = attributes,
            setErrorMessage = { errorCodes -> errorHandler.getLocalizedErrorMessage(errorCodes, languageCode) },
            method = { get("/taxis/$driverId") }
        )
    }

    @OptIn(InternalAPI::class)
    suspend fun createTaxi(taxiDto: TaxiDto, languageCode: String): TaxiDto {
        return client.tryToExecute(
            api = APIs.TAXI_API,
            attributes = attributes,
            setErrorMessage = { errorCodes -> errorHandler.getLocalizedErrorMessage(errorCodes, languageCode) },
            method = { post("/taxi") { body = Json.encodeToString(TaxiDto.serializer(), taxiDto) } }
        )
    }

    @OptIn(InternalAPI::class)
    suspend fun editTaxi(id: String, taxiDto: TaxiDto, languageCode: String): TaxiDto {
        return client.tryToExecute(
            api = APIs.TAXI_API,
            attributes = attributes,
            setErrorMessage = { errorCodes -> errorHandler.getLocalizedErrorMessage(errorCodes, languageCode) },
            method = { put("/taxi/$id") { body = Json.encodeToString(TaxiDto.serializer(), taxiDto) } }
        )
    }

    suspend fun deleteTaxi(id: String, languageCode: String): TaxiDto {
        return client.tryToExecute(
            api = APIs.TAXI_API,
            attributes = attributes,
            setErrorMessage = { errorCodes -> errorHandler.getLocalizedErrorMessage(errorCodes, languageCode) },
            method = { delete("/taxi/$id") }
        )
    }

    suspend fun findTaxisByQuery(
        page: Int,
        limit: Int,
        status: Boolean?,
        color: Long?,
        seats: Int?,
        query: String?,
        language: String
    ): PaginationResponse<TaxiDto> {
        return client.tryToExecute(
            api = APIs.TAXI_API,
            attributes = attributes,
            setErrorMessage = { errorCodes -> errorHandler.getLocalizedErrorMessage(errorCodes, language) }
        ) {
            get("/taxis/search") {
                parameter("page", page)
                parameter("limit", limit)
                parameter("status", status)
                parameter("color", color)
                parameter("seats", seats)
                parameter("query", query)
            }
        }
    }

    @OptIn(InternalAPI::class)
    suspend fun createTrip(trip: TripDto, languageCode: String): TripDto {
        return client.tryToExecute(
            api = APIs.TAXI_API,
            attributes = attributes,
            setErrorMessage = { errorCodes -> errorHandler.getLocalizedErrorMessage(errorCodes, languageCode) },
            method = { post("/trip") { body = Json.encodeToString(TripDto.serializer(), trip) } }
        )
    }

    suspend fun getTaxiTrips(driverId: String): Flow<TripDto> {
        return client.tryToExecuteWebSocket<TripDto>(
            api = APIs.TAXI_API,
            attributes = attributes,
            path = "/trip/taxi/$driverId",
        )
    }

    suspend fun getDeliveryTrips(deliveryId: String): Flow<TripDto> {
        return client.tryToExecuteWebSocket<TripDto>(
            api = APIs.TAXI_API,
            attributes = attributes,
            path = "/trip/delivery/$deliveryId",
        )
    }

    suspend fun trackOrderRequest(tripId: String): Flow<TripDto> {
        return client.tryToExecuteWebSocket<TripDto>(
            api = APIs.TAXI_API,
            attributes = attributes,
            path = "/trip/track/$tripId",
        )
    }

    suspend fun getTripById(tripId: String, languageCode: String): TripDto {
        return client.tryToExecute(
            api = APIs.TAXI_API,
            attributes = attributes,
            setErrorMessage = { errorCodes -> errorHandler.getLocalizedErrorMessage(errorCodes, languageCode) },
            method = { get("/trip/$tripId") }
        )
    }

    suspend fun getTripByOrderId(orderId: String, languageCode: String): TripDto {
        return client.tryToExecute(
            api = APIs.TAXI_API,
            attributes = attributes,
            setErrorMessage = { errorCodes -> errorHandler.getLocalizedErrorMessage(errorCodes, languageCode) },
            method = { get("/trip/user/$orderId") }
        )
    }

    suspend fun getTripsHistoryForUser(
        userId: String, page: Int, limit: Int, languageCode: String
    ): PaginationResponse<TripDto> {
        return client.tryToExecute<PaginationResponse<TripDto>>(
            api = APIs.TAXI_API,
            attributes = attributes,
            setErrorMessage = { errorCodes -> errorHandler.getLocalizedErrorMessage(errorCodes, languageCode) },
            method = { get("/trip/client/$userId?page=$page&&limit=$limit") }
        )
    }

    @OptIn(InternalAPI::class)
    suspend fun updateTrip(taxiId: String, tripId: String, driverId: String, languageCode: String): TripDto {
        return client.tryToExecute(
            api = APIs.TAXI_API,
            attributes = attributes,
            setErrorMessage = { errorCodes -> errorHandler.getLocalizedErrorMessage(errorCodes, languageCode) }
        ) {
            val formData = FormDataContent(
                Parameters.build {
                    append("taxiId", taxiId)
                    append("driverId", driverId)
                }
            )
            put("/trip/update/$tripId") { body = formData }
        }
    }

    suspend fun deleteTaxiByDriverId(id: String): Boolean {
        return client.tryToExecute<Boolean>(
            api = APIs.TAXI_API,
            attributes = attributes,
            method = { delete("/taxi/driver/$id") }
        )
    }

    suspend fun getActiveTripsByUserId(userId: String, languageCode: String): List<TripDto> {
        return client.tryToExecute<List<TripDto>>(
            api = APIs.TAXI_API,
            attributes = attributes,
            setErrorMessage = { errorCodes -> errorHandler.getLocalizedErrorMessage(errorCodes, languageCode) },
            method = { get("/trip/actives/$userId") }
        )
    }

    suspend fun deleteTaxiAndTripsCollections(): Boolean {
        return client.tryToExecute<Boolean>(
            api = APIs.TAXI_API,
            attributes = attributes,
            method = { delete("/taxi-trips") }
        )
    }
}
