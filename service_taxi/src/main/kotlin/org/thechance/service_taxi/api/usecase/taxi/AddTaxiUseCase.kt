package org.thechance.service_taxi.api.usecase.taxi

import org.thechance.service_taxi.domain.entity.Taxi

interface AddTaxiUseCase {
    suspend operator fun invoke(taxi: Taxi): Boolean
}