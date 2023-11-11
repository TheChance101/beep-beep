package data.gateway.remote

import data.remote.mapper.toTripEntity
import data.remote.model.BaseResponse
import data.remote.model.LocationDto
import data.remote.model.OrderDto
import domain.entity.Order
import domain.gateway.remote.IMapRemoteGateway
import domain.utils.NotFoundException
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.submitForm
import io.ktor.http.HttpMethod
import io.ktor.http.Parameters
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MapRemoteGateway(client: HttpClient) : IMapRemoteGateway, BaseRemoteGateway(client = client) {
    override suspend fun getOrders(): Flow<Order> {
        val result = client.tryToExecuteWebSocket<OrderDto>("/trip/incoming-delivery-orders")
        return result.map { it.toTripEntity() }
    }

    override suspend fun sendLocation(location: LocationDto, tripId: String) {
        client.tryToExecuteWebSocket<Order>("/location/sender/$tripId")
    }

    override suspend fun updateTrip(taxiId: String, tripId: String): Order {
        val result = tryToExecute<BaseResponse<OrderDto>> {
            submitForm(
                url = ("/trip/update/delivery-order"),
                formParameters = Parameters.build {
                    append("tripId", tripId)
                    append("taxiId", taxiId)
                },
                block = { method = HttpMethod.Put }
            )
        }.value ?: throw NotFoundException()
        return result.toTripEntity()
    }
}