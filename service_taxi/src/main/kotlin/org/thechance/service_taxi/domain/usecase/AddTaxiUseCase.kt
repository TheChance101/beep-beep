package org.thechance.service_taxi.domain.usecase

import org.thechance.service_taxi.domain.entity.Taxi

interface AddTaxiUseCase {
    suspend operator fun invoke(taxi: Taxi): Boolean
}