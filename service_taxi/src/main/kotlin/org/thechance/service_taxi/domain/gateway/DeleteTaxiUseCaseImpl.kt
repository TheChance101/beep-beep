package org.thechance.service_taxi.domain.gateway

import org.koin.core.annotation.Single
import org.thechance.service_taxi.domain.usecase.DeleteTaxiUseCase

@Single
class DeleteTaxiUseCaseImpl(private val taxiGateway: TaxiGateway) : DeleteTaxiUseCase {
    override suspend fun invoke(taxiId: String): Boolean {
        return taxiGateway.deleteTaxi(taxiId)
    }
}