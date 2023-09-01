package org.thechance.api_gateway.endpoints.gateway

import org.thechance.api_gateway.data.model.BasePaginationResponse
import org.thechance.api_gateway.data.model.Taxi
import java.util.Locale

interface ITaxiGateway {

    suspend fun getAllTaxi(locale: Locale,page: Int, limit: Int): BasePaginationResponse<Taxi>

    suspend fun getTaxiById(id: String, locale: Locale): Taxi

    suspend fun createTaxi(
        taxi : Taxi,
        locale: Locale
    ): Taxi


    suspend fun editTaxi(
        id: String,
        taxi : Taxi,
        locale: Locale
    ): Taxi


    suspend fun deleteTaxi(id: String, locale: Locale): Taxi
}