package domain.gateway.remote

import domain.entity.Order
import domain.entity.PaginationItems
import kotlinx.coroutines.flow.Flow


interface IOrderRemoteGateway {

    suspend fun getCurrentOrders(restaurantId: String): Flow<Order>
    suspend fun getActiveOrders(restaurantId: String): List<Order>
    suspend fun updateOrderState(orderId: String): Order
    suspend fun cancelOrder(orderId: String): Order
    suspend fun getOrdersHistory(restaurantId: String, page: Int, limit: Int): PaginationItems<Order>

    suspend fun getOrdersRevenueByDaysBefore(
        restaurantId: String,
        daysBack: Int
    ): List<Map<String, Double>>

    suspend fun getOrdersCountByDaysBefore(
        restaurantId: String, daysBack: Int
    ): List<Map<String, Int>>
}