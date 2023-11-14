package domain.usecase

import domain.entity.Location
import domain.entity.Trip
import domain.gateway.remote.ITripRemoteGateway
import kotlinx.coroutines.flow.last


interface IManageTripUseCase {
    suspend fun findNewTrip(): Trip
    suspend fun updateTripLocation(location: Location, tripId: String)
    suspend fun updateTripStatus(tripId: String)
}

class ManageTripUseCase(
    private val tripGateway: ITripRemoteGateway,
) : IManageTripUseCase {
    override suspend fun findNewTrip(): Trip {
        return tripGateway.getTrips().last()
    }
    override suspend fun updateTripLocation(location: Location, tripId: String) {
        tripGateway.sendLocation(location = location, tripId = tripId)
    }

    override suspend fun updateTripStatus(tripId: String) {
        tripGateway.updateTrip("0" ,tripId)
    }
}