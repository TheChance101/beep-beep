package org.thechance.service_taxi.domain.usecase.taxi

import org.koin.core.annotation.Single
import org.thechance.service_taxi.domain.entity.Taxi
import org.thechance.service_taxi.api.usecase.taxi.AddTaxiUseCase
import org.thechance.service_taxi.domain.gateway.TaxiGateway

@Single
class AddTaxiUseCaseImpl(private val taxiGateway: TaxiGateway) : AddTaxiUseCase {
    override suspend operator fun invoke(taxi: Taxi): Boolean {
        return taxiGateway.addTaxi(taxi)
    }
}