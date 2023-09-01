package org.thechance.api_gateway.data.service

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.util.*
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Single
import org.thechance.api_gateway.data.model.PaginationResponse
import org.thechance.api_gateway.data.model.taxi.TaxiDto
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
}