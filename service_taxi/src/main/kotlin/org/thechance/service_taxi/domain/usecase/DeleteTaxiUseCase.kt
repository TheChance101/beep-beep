package org.thechance.service_taxi.domain.usecase

interface DeleteTaxiUseCase {
    suspend operator fun invoke(taxiId: String): Boolean
}