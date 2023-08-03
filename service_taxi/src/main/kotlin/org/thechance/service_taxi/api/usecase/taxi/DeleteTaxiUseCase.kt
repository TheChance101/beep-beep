package org.thechance.service_taxi.api.usecase.taxi

interface DeleteTaxiUseCase {
    suspend operator fun invoke(taxiId: String): Boolean
}