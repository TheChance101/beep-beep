package org.thechance.service_taxi.domain.usecase

import org.koin.core.annotation.Single
import org.thechance.service_taxi.domain.datasource.TaxiDataSource
import org.thechance.service_taxi.domain.entity.Taxi

@Single
class AddTaxiUseCase(private val taxiDataSource: TaxiDataSource) {
    suspend operator fun invoke(taxi: Taxi): Boolean {
        return taxiDataSource.addTaxi(taxi)
    }
}