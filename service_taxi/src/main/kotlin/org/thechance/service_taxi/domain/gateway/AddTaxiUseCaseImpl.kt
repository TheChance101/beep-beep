package org.thechance.service_taxi.domain.gateway

import org.koin.core.annotation.Single
import org.thechance.service_taxi.domain.entity.Taxi
import org.thechance.service_taxi.domain.usecase.AddTaxiUseCase

@Single
class AddTaxiUseCaseImpl(private val taxiGateway: TaxiGateway) : AddTaxiUseCase {
    override suspend operator fun invoke(taxi: Taxi): Boolean {
        return taxiGateway.addTaxi(taxi)
    }
}