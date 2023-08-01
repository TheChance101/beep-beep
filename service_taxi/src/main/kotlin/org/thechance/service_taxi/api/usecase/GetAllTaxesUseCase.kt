package org.thechance.service_taxi.api.usecase

import org.thechance.service_taxi.domain.entity.Taxi

interface GetAllTaxesUseCase {
    suspend operator fun invoke(): List<Taxi>
}