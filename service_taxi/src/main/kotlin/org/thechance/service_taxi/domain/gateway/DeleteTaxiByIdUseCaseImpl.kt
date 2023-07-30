package org.thechance.service_taxi.domain.gateway

import org.koin.core.annotation.Single
import org.thechance.service_taxi.domain.usecase.DeleteTaxiByIdUseCase

@Single
class DeleteTaxiByIdUseCaseImpl(private val taxiGateway: TaxiGateway) : DeleteTaxiByIdUseCase {
    override suspend fun invoke(taxiId: String): Boolean {
        return taxiGateway.deleteTaxi(taxiId)
    }
}