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
        return client.tryToExecuteWebSocket<OrderDto>("192.168.1.100:8080/trip/incoming-delivery-orders")
            .map { it.toTripEntity() }
    }

    override suspend fun sendLocation(location: LocationDto, tripId: String) {
        client.tryToExecuteWebSocket<Order>("/location/sender/$tripId")
    }

    @OptIn(InternalAPI::class)
    override suspend fun acceptOrder(
        taxiId: String,
        tripId: String,
        clientId: String,
    ): Order {
        val result = tryToExecute<BaseResponse<OrderDto>> {
            val formData = FormDataContent(Parameters.build {
                append("tripId", tripId)
                append("taxiId", taxiId)
                append("userId", clientId)
            })
            put("/trip/approve") {
                body = formData
            }
        }.value ?: throw Exception()

        return result.toTripEntity()
    }

    @OptIn(InternalAPI::class)
    override suspend fun updateOrderAsReceived(
        tripId: String,
        clientId: String,
    ): Order {
        val result = tryToExecute<BaseResponse<OrderDto>> {
            val formData = FormDataContent(Parameters.build {
                append("tripId", tripId)
                append("userId", clientId)
            })
            put("/trip/received") {
                body = formData
            }
        }.value ?: throw Exception()

        return result.toTripEntity()
    }

    @OptIn(InternalAPI::class)
    override suspend fun updateOrderAsDelivered(
        tripId: String,
        clientId: String,
    ): Order {
        val result = tryToExecute<BaseResponse<OrderDto>> {
            val formData = FormDataContent(Parameters.build {
                append("tripId", tripId)
                append("userId", clientId)
            })
            put("/trip/finish") {
                body = formData
            }
        }.value ?: throw Exception()

        return result.toTripEntity()
    }

}