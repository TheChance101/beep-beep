package domain.gateway.remote

import domain.entity.Order


interface IOrderRemoteGateway {

    suspend fun getCurrentOrders(restaurantId: String): List<Order>?
    suspend fun getOrdersHistory(restaurantId: String): List<Order>
    suspend fun updateOrderState(orderId: String, orderState: Int): Order?
    suspend fun getOrderById(orderId: String): Order?

}