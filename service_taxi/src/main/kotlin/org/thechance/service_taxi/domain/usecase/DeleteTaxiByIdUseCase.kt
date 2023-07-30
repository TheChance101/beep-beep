package org.thechance.service_taxi.domain.usecase

interface DeleteTaxiByIdUseCase {
    suspend operator fun invoke(taxiId: String): Boolean
}