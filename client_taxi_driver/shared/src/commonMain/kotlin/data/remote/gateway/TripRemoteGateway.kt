package data.remote.gateway

import data.remote.mapper.toDto
import data.remote.mapper.toEntity
import data.remote.model.BaseResponse
import data.remote.model.LocationDto
import data.remote.model.TripDto
import domain.NotFoundedException
import domain.entity.Location
import domain.entity.Trip
import domain.gateway.remote.ITripRemoteGateway
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.submitForm
import io.ktor.http.HttpMethod
import io.ktor.http.Parameters
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TripRemoteGateway(client: HttpClient) : ITripRemoteGateway,
    BaseRemoteGateway(client = client) {

    override suspend fun getTrips(): Flow<Trip> {
        return client.tryToExecuteWebSocket<TripDto>("/trip/incoming-taxi-rides")
            .map { it.toEntity() }
    }

    override suspend fun sendLocation(location: Location, tripId: String) {
        client.tryToSendWebSocketData<LocationDto>(
            path = "/location/sender/$tripId",
            data = location.toDto()
        )
    }

    override suspend fun updateTrip(tripId: String) {
        val result = tryToExecute<BaseResponse<TripDto>> {
            submitForm(
                url = ("/trip/update/taxi-ride"),
                formParameters = Parameters.build {
                    append("tripId", tripId)
                    append("taxiId", TAXI_ID)
                },
                block = { method = HttpMethod.Put }
            )
        }.value ?: throw NotFoundedException()
    }

    private companion object{
        const val TAXI_ID = "653d6d4f5a253b12181fa2de"
    }

}