package data.gateway.remote

import data.remote.model.BaseResponse
import data.remote.model.LocationDto
import data.remote.model.TripDto
import domain.gateway.remote.IMapRemoteGateway
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.http.Parameters
import io.ktor.util.InternalAPI

//TODO IMPORTANT, AFTER ADDING THE ENTITY RETURN TRIP INSTEAD OF TRIPDTO
class MapRemoteGateway(client: HttpClient) : IMapRemoteGateway,
    BaseRemoteGateway(client = client) {
    override suspend fun sendLocation(location: LocationDto, tripId: String) {
      client.tryToSendWebSocketData(
            data = location,
            path = "/location/sender/$tripId"
        )
    }

    override suspend fun getTripById(tripId: String): TripDto {
       val result = tryToExecute<BaseResponse<TripDto>> {
            get("/trip/$tripId")
        }.value ?: throw Exception()

        return result
    }

    @OptIn(InternalAPI::class)
    override suspend fun approveTrip(taxiId: String, tripId: String, driverId: String): TripDto {
        val result = tryToExecute<BaseResponse<TripDto>> {
            val formData = FormDataContent(Parameters.build {
                append("tripId", tripId)
                append("taxiId", taxiId)
                append("driverId", driverId)
            })
            put("/trip/approve") {
                body = formData
            }
        }.value ?: throw Exception()

        return result
    }

    @OptIn(InternalAPI::class)
    override suspend fun finishTrip(tripId: String, driverId: String): TripDto {
        val result = tryToExecute<BaseResponse<TripDto>> {
            val formData = FormDataContent(Parameters.build {
                append("tripId", tripId)
                append("driverId", driverId)
            })
            put("/trip/finish$tripId") {
                body = formData
            }
        }.value ?: throw Exception()

        return result
    }

    override suspend fun deleteTrip(tripId: String): TripDto {
        val result = tryToExecute<BaseResponse<TripDto>> {
            delete("trip/cancel$tripId")
        }.value ?: throw Exception()

        return result
    }

}