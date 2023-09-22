package domain.usecase

import domain.entity.Location
import domain.entity.Trip
import domain.gateway.remote.IMapRemoteGateway

interface IManageOrderUseCase {
    suspend fun sendLocation(location: Location, tripId: String)
    suspend fun getTripById(tripId: String): Trip
    suspend fun approveTrip(taxiId: String, tripId: String, driverId: String): Trip
    suspend fun finishTrip(tripId: String, driverId: String): Trip
    suspend fun deleteTrip(tripId: String): Trip
}

class ManageOrderUseCase(private val remoteGateway: IMapRemoteGateway) : IManageOrderUseCase {
    override suspend fun sendLocation(location: Location, tripId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getTripById(tripId: String): Trip {
        TODO("Not yet implemented")
    }

    override suspend fun approveTrip(taxiId: String, tripId: String, driverId: String): Trip {
        TODO("Not yet implemented")
    }

    override suspend fun finishTrip(tripId: String, driverId: String): Trip {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTrip(tripId: String): Trip {
        TODO("Not yet implemented")
        // return remoteGateway.deleteTrip(tripId)
    }

}