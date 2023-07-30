package org.thechance.service_taxi.domain.gateway

import org.koin.core.annotation.Single
import org.thechance.service_taxi.domain.entity.Taxi
import org.thechance.service_taxi.domain.usecase.GetTaxiByIdUseCase

@Single
class GetTaxiByIdUseCaseImpl(private val taxiGateway: TaxiGateway) : GetTaxiByIdUseCase {
    override suspend fun invoke(taxiId: String): Taxi? {
        return taxiGateway.getTaxiById(taxiId)
    }
}