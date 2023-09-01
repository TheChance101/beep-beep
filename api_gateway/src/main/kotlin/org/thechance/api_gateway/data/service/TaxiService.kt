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
import java.util.*


@Single
class TaxiService(private val client: HttpClient, private val errorHandler: ErrorHandler) {


     suspend fun getAllTaxi(locale: Locale, page: Int, limit: Int): PaginationResponse<TaxiDto> {
        return client.tryToExecute(
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

     suspend fun getTaxiById(
        id: String,
        locale: Locale
    ): TaxiDto {
        return client.tryToExecute(
            api = APIs.TAXI_API,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) {
            get("/taxi/$id")
        }
    }

    @OptIn(InternalAPI::class)
     suspend fun createTaxi(
        taxiDto: TaxiDto,
        locale: Locale
    ): TaxiDto {
        return client.tryToExecute(
            api = APIs.TAXI_API,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
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
        locale: Locale
    ): TaxiDto {
        return client.tryToExecute(
            api = APIs.TAXI_API,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) {
            put("/taxi/$id") {
                body = Json.encodeToString(TaxiDto.serializer(), taxiDto)
            }
        }
    }

     suspend fun deleteTaxi(
        id: String,
        locale: Locale
    ): TaxiDto {
        return client.tryToExecute(
            api = APIs.TAXI_API,
            setErrorMessage = { errorCodes ->
                errorHandler.getLocalizedErrorMessage(errorCodes, locale)
            }
        ) {
            delete("/taxi/$id")
        }
    }
}