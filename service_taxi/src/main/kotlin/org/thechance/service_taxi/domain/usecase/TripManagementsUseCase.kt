package org.thechance.service_taxi.domain.usecase

import org.thechance.service_taxi.domain.entity.Trip

interface TripManagementsUseCase {

    suspend fun updateTripState(trip: Trip): Boolean // update  trip state to canceled ,start/complete
    suspend fun getAllTripsByDriverId(driverId: String, page: Int, limit: Int): List<Trip>

}