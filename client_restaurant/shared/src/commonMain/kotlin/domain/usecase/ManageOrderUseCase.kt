package domain.usecase

import domain.entity.Order
import domain.gateway.remote.IOrderRemoteGateway

interface IManageOrderUseCase {
    suspend fun getCurrentOrders(restaurantId: String): List<Order>
    suspend fun updateOrderState(orderId: String): Order
    suspend fun getOrdersHistory(restaurantId: String, page: Int, limit: Int): List<Order>
    suspend fun getOrdersRevenueByDaysBefore(
        restaurantId: String,
        daysBack: Int
    ): List<Pair<String, Double>>

    suspend fun getOrdersCountByDaysBefore(
        restaurantId: String,
        daysBack: Int
    ): List<Pair<String, Double>>
}

class ManageOrderUseCase(private val orderRemoteGateway: IOrderRemoteGateway) :
    IManageOrderUseCase {
    override suspend fun getCurrentOrders(restaurantId: String): List<Order> {
        return emptyList()
        // return orderRemoteGateway.getCurrentOrders(restaurantId)
    }

    override suspend fun updateOrderState(orderId: String): Order {
        return orderRemoteGateway.updateOrderState(orderId)
    }

    override suspend fun getOrdersHistory(
        restaurantId: String,
        page: Int,
        limit: Int
    ): List<Order> {
        val result = orderRemoteGateway.getOrdersHistory(restaurantId, page, limit)
        println("getOrdersHistory from use case: ${result}")
        return result.items
    }

    override suspend fun getOrdersRevenueByDaysBefore(
        restaurantId: String,
        daysBack: Int
    ): List<Pair<String, Double>> {
        return orderRemoteGateway.getOrdersRevenueByDaysBefore(restaurantId, daysBack)
            .flatMap { map ->
                map.entries.map { it.key to it.value }
            }
    }

    override suspend fun getOrdersCountByDaysBefore(
        restaurantId: String,
        daysBack: Int
    ): List<Pair<String, Double>> {
        return orderRemoteGateway.getOrdersCountByDaysBefore(restaurantId, daysBack)
            .flatMap { map ->
                map.entries.map { it.key to it.value.toDouble() }
            }
    }

}
