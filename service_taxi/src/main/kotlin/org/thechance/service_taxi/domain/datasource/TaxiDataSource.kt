package org.thechance.service_taxi.domain.datasource

import org.thechance.service_taxi.domain.entity.Taxi

interface TaxiDataSource {
    suspend fun addTaxi(taxi: Taxi): Boolean
    suspend fun getTaxiById(taxiId: String): Taxi?
    suspend fun getAllTaxes(): List<Taxi>
    suspend fun deleteTaxi(taxiId: String): Boolean
    suspend fun updateTaxi(taxiId: String, taxi: Taxi): Boolean
}