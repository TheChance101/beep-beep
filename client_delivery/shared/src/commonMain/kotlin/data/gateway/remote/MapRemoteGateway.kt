package data.gateway.remote

import data.remote.mapper.toTripEntity
import data.remote.model.BaseResponse
import data.remote.model.LocationDto
import data.remote.model.OrderDto
import domain.entity.Order
import domain.gateway.remote.IMapRemoteGateway
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.put
import io.ktor.http.Parameters
import io.ktor.util.InternalAPI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MapRemoteGateway(client: HttpClient) : IMapRemoteGateway,
    BaseRemoteGateway(client = client) {
    override suspend fun getOrders(): Flow<Order> {
        return client.tryToExecuteWebSocket<OrderDto>("trip/incoming-delivery-orders")
            .map { it.toTripEntity() }
    }

    override suspend fun sendLocation(location: LocationDto, tripId: String) {
        client.tryToExecuteWebSocket<Order>("/location/sender/$tripId")
    }

    @OptIn(InternalAPI::class)
    override suspend fun updateTrip(
        taxiId: String,
        tripId: String
    ): Order {
        val result = tryToExecute<BaseResponse<OrderDto>> {
            val formData = FormDataContent(Parameters.build {
                append("tripId", tripId)
                append("taxiId", taxiId)
            })
            put("/trip/update") {
                body = formData
            }
        }.value ?: throw Exception()

        return result.toTripEntity()
    }

}