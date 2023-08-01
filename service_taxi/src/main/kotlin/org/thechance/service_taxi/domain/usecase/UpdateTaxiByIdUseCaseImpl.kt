package org.thechance.service_taxi.domain.usecase

import org.koin.core.annotation.Single
import org.thechance.service_taxi.domain.entity.Taxi
import org.thechance.service_taxi.api.usecase.UpdateTaxiByIdUseCase
import org.thechance.service_taxi.domain.gateway.TaxiGateway

@Single
class UpdateTaxiByIdUseCaseImpl(private val taxiGateway: TaxiGateway) : UpdateTaxiByIdUseCase {
    override suspend fun invoke(taxiId: String, taxi: Taxi): Boolean {
        return taxiGateway.updateTaxi(taxiId, taxi)
    }
}