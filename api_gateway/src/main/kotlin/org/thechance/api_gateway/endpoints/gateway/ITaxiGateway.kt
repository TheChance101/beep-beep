package org.thechance.api_gateway.endpoints.gateway

import org.thechance.api_gateway.data.model.TaxiResource
import java.util.Locale

interface ITaxiGateway {

    suspend fun getAllTaxi(locale: Locale,page: Int, limit: Int): List<TaxiResource>

    suspend fun getTaxiById(id: String, locale: Locale): TaxiResource

    suspend fun createTaxi(
        plateNumber: String,
        color: Long,
        type: String,
        driverId: String,
        seats: Int,
        isAvailable: Boolean,
        locale: Locale
    ): TaxiResource


    suspend fun editTaxi(
        id: String,
        plateNumber: String,
        color: Long,
        type: String,
        driverId: String,
        seats: Int,
        isAvailable: Boolean,
        locale: Locale
    ): TaxiResource


    suspend fun deleteTaxi(id: String, locale: Locale): TaxiResource
}