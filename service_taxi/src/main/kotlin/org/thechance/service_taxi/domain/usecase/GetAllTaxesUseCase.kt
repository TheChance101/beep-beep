package org.thechance.service_taxi.domain.usecase

import org.koin.core.annotation.Single
import org.thechance.service_taxi.domain.gateway.TaxiGateway
import org.thechance.service_taxi.domain.entity.Taxi

@Single
class GetAllTaxesUseCase(private val taxiGateway: TaxiGateway) {
    suspend operator fun invoke(): List<Taxi> {
        return taxiGateway.getAllTaxes()
    }
}