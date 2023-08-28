package org.thechance.api_gateway.endpoints.gateway

import org.thechance.api_gateway.data.model.TaxiResource
import java.util.Locale

interface ITaxiGateway {

    suspend fun getAllTaxi(permissions: List<Int>, locale: Locale,page: Int, limit: Int): List<TaxiResource>

    suspend fun getTaxiById(id: String, permissions: List<Int>, locale: Locale): TaxiResource


    suspend fun createTaxi(
        plateNumber: String,
        color: Int,
        type: String,
        driverId: String,
        seats: Int,
        permissions: List<Int>,
        locale: Locale
    ): TaxiResource

    suspend fun updateTaxi(
        id: String,
        plateNumber: String,
        color: Int,
        type: String,
        driverId: String,
        seats: Int,
        permissions: List<Int>,
        locale: Locale
    ): TaxiResource

    suspend fun deleteTaxi(id: String, permissions: List<Int>, locale: Locale): TaxiResource
}