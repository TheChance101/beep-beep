package org.thechance.service_taxi.domain.usecase.taxi

import org.koin.core.annotation.Single
import org.thechance.service_taxi.api.usecase.taxi.GetAllTaxesUseCase
import org.thechance.service_taxi.domain.entity.Taxi
import org.thechance.service_taxi.domain.gateway.TaxiGateway

@Single
class GetAllTaxesUseCaseImpl(private val taxiGateway: TaxiGateway) : GetAllTaxesUseCase {
    override suspend operator fun invoke(page: Int, limit: Int): List<Taxi> {
        return taxiGateway.getAllTaxes(page, limit)
    }
}