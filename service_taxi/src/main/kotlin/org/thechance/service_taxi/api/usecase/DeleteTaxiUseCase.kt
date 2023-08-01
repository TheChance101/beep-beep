package org.thechance.service_taxi.api.usecase

interface DeleteTaxiUseCase {
    suspend operator fun invoke(taxiId: String): Boolean
}