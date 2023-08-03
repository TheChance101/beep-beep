package org.thechance.service_taxi.api.usecase.trip

interface DeleteTripUseCase {
    suspend operator fun invoke(tripId: String): Boolean
}