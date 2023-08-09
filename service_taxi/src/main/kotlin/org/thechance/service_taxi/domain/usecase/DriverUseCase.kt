package org.thechance.service_taxi.domain.usecase

import org.thechance.service_taxi.domain.entity.Trip

interface DriverUseCase {
    suspend fun approveTrip(driverId: String, taxiId: String, tripId: String)
    suspend fun finishTrip(driverId: String, tripId: String)
    suspend fun getTripsByDriverId(driverId: String, page: Int, limit: Int): List<Trip>
}