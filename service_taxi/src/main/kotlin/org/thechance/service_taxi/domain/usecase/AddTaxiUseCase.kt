package org.thechance.service_taxi.domain.usecase

import org.koin.core.annotation.Single
import org.thechance.service_taxi.domain.gateway.TaxiGateway
import org.thechance.service_taxi.domain.entity.Taxi

@Single
class AddTaxiUseCase(private val taxiGateway: TaxiGateway) {
    suspend operator fun invoke(taxi: Taxi): Boolean {
        return taxiGateway.addTaxi(taxi)
    }
}