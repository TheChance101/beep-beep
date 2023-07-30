package org.thechance.service_taxi.domain.usecase

import org.thechance.service_taxi.domain.entity.Taxi

interface UpdateTaxiByIdUseCase {
    suspend operator fun invoke(taxiId: String, taxi: Taxi): Boolean
}