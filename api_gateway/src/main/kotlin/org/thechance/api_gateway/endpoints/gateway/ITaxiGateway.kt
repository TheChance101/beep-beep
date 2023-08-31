package org.thechance.api_gateway.endpoints.gateway

import org.thechance.api_gateway.data.model.TaxiResource
import java.util.Locale

interface ITaxiGateway {

    suspend fun getAllTaxi(locale: Locale,page: Int, limit: Int): List<TaxiResource>

    suspend fun getTaxiById(id: String, locale: Locale): TaxiResource

    suspend fun createTaxi(
        taxi : TaxiResource,
        locale: Locale
    ): TaxiResource


    suspend fun editTaxi(
        id: String,
        taxi : TaxiResource,
        locale: Locale
    ): TaxiResource


    suspend fun deleteTaxi(id: String, locale: Locale): TaxiResource
}