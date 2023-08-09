package org.thechance.service_taxi.domain.usecase

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.core.annotation.Single
import org.thechance.service_taxi.domain.entity.Trip
import org.thechance.service_taxi.domain.entity.TripUpdateRequest
import org.thechance.service_taxi.domain.gateway.DataBaseGateway
import org.thechance.service_taxi.domain.util.ResourceNotFoundException

interface IDiscoverTripsUseCase {
    suspend fun getTripsByDriverId(driverId: String, page: Int, limit: Int): List<Trip>
    suspend fun getTripsByClientId(clientId: String, page: Int, limit: Int): List<Trip>
    suspend fun approveTrip(driverId: String, taxiId: String, tripId: String)
    suspend fun finishTrip(driverId: String, tripId: String)
}

@Single
class DiscoverTripsUseCase(
    private val dataBaseGateway: DataBaseGateway
) : IDiscoverTripsUseCase {
    override suspend fun approveTrip(driverId: String, taxiId: String, tripId: String) {
        dataBaseGateway.getTripById(tripId) ?: throw ResourceNotFoundException
        dataBaseGateway.updateTrip(
            TripUpdateRequest(
                id = tripId,
                taxiId = taxiId,
                driverId = driverId,
                startDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            )
        )
    }

    override suspend fun finishTrip(driverId: String, tripId: String) {
        dataBaseGateway.getTripById(tripId) ?: throw ResourceNotFoundException
        dataBaseGateway.updateTrip(
            TripUpdateRequest(
                tripId,
                endDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            )
        )
    }
    override suspend fun getTripsByDriverId(
        driverId: String,
        page: Int,
        limit: Int
    ): List<Trip> {
        return dataBaseGateway.getDriverTripsHistory(driverId, page, limit)
    }
    override suspend fun getTripsByClientId(
        clientId: String,
        page: Int,
        limit: Int
    ): List<Trip> {
        return dataBaseGateway.getClientTripsHistory(clientId, page, limit)
    }
}