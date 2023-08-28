package org.thechance.api_gateway.endpoints.gateway

import org.thechance.api_gateway.data.model.TaxiResource
import java.util.Locale

interface ITaxiGateway {

    suspend fun getAllTaxiDrivers(permissions: List<Int>, locale: Locale): List<TaxiResource>

    suspend fun getTaxiDriverById(id: String, permissions: List<Int>, locale: Locale): TaxiResource


    suspend fun createTaxiDriver(
        plateNumber: String,
        color: Int,
        type: String,
        driverId: String,
        seats: Int,
        permissions: List<Int>,
        locale: Locale
    ): TaxiResource

    suspend fun updateTaxiDriver(
        id: String,
        plateNumber: String,
        color: Int,
        type: String,
        driverId: String,
        seats: Int,
        permissions: List<Int>,
        locale: Locale
    ): TaxiResource

    suspend fun deleteTaxiDriver(id: String, permissions: List<Int>, locale: Locale): TaxiResource
}