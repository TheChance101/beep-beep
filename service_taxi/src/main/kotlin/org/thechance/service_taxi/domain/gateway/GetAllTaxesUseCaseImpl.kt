package org.thechance.service_taxi.domain.gateway

import org.koin.core.annotation.Single
import org.thechance.service_taxi.domain.entity.Taxi
import org.thechance.service_taxi.domain.usecase.GetAllTaxesUseCase

@Single
class GetAllTaxesUseCaseImpl(private val taxiGateway: TaxiGateway) : GetAllTaxesUseCase {
    override suspend operator fun invoke(): List<Taxi> {
        return taxiGateway.getAllTaxes()
    }
}