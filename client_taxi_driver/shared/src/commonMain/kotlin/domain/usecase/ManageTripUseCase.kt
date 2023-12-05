package domain.usecase

import domain.entity.Location
import domain.entity.Trip
import domain.gateway.local.ILocalConfigurationGateway
import domain.gateway.remote.ITripRemoteGateway
import kotlinx.coroutines.flow.Flow


interface IManageTripUseCase {
    suspend fun findNewTrip(): Flow<Trip>
    suspend fun updateTripLocation(location: Location, tripId: String)
    suspend fun updateTripStatus(tripId: String)
    suspend fun getTaxiId(): String
}

class ManageTripUseCase(
    private val tripGateway: ITripRemoteGateway,
    private val localGateWay: ILocalConfigurationGateway
) : IManageTripUseCase {
    override suspend fun findNewTrip(): Flow<Trip> {
        return tripGateway.getTrips()
    }
    override suspend fun updateTripLocation(location: Location, tripId: String) {
        tripGateway.sendLocation(location = location, tripId = tripId)
    }

    override suspend fun updateTripStatus(tripId: String) {
        val taxiId = localGateWay.getTaxiId()
        tripGateway.updateTrip(tripId,taxiId)
    }

    override suspend fun getTaxiId(): String {
        TODO("Not yet implemented")
    }
}