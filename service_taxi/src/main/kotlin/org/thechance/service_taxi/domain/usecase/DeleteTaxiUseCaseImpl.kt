package org.thechance.service_taxi.domain.usecase

import org.koin.core.annotation.Single
import org.thechance.service_taxi.api.usecase.taxi.DeleteTaxiUseCase
import org.thechance.service_taxi.domain.gateway.TaxiGateway

@Single
class DeleteTaxiUseCaseImpl(private val taxiGateway: TaxiGateway) : DeleteTaxiUseCase {
    override suspend fun invoke(taxiId: String): Boolean {
        return taxiGateway.deleteTaxi(taxiId)
    }
}