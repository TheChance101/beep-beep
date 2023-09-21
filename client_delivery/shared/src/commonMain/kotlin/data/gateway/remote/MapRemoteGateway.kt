package data.gateway.remote

import data.remote.model.LocationDto
import data.remote.model.TripDto
import domain.gateway.remote.IMapRemoteGateway
import io.ktor.client.HttpClient


class MapRemoteGateway(client: HttpClient) : IMapRemoteGateway,
    BaseRemoteGateway(client = client) {
    override suspend fun sendLocation(location: LocationDto, tripId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getTripById(tripId: String): TripDto {
        TODO("Not yet implemented")
    }

    override suspend fun approveTrip(taxiId: String, tripId: String, driverId: String): TripDto {
        TODO("Not yet implemented")
    }

    override suspend fun finishTrip(tripId: String, driverId: String): TripDto {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTrip(tripId: String): TripDto {
        TODO("Not yet implemented")
    }

}