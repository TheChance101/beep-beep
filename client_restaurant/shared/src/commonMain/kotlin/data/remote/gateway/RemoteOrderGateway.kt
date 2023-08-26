package data.remote.gateway

import domain.entity.Order
import domain.gateway.IRemoteOrderGateway
import io.ktor.client.HttpClient


class RemoteOrderGateway(private val client: HttpClient) : IRemoteOrderGateway {

    override suspend fun getCurrentOrders(restaurantId: String): List<Order> {
        return emptyList()
    }

    override suspend fun getOrdersHistory(restaurantId: String): List<Order> {
        return emptyList()
    }

    override suspend fun updateOrderState(orderId: String, orderState: Int): Order {
        TODO()
    }

    override suspend fun getOrderById(orderId: String): Order? {
        return null
    }

}