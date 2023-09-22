package domain.usecase

import domain.entity.Order
import domain.entity.OrderState
import domain.gateway.remote.IOrderRemoteGateway
import domain.utils.Constant
import presentation.base.RequestException

interface IManageOrderUseCase {
    suspend fun getCurrentOrders(restaurantId: String): List<Order>
    suspend fun updateOrderState(orderId: String, orderState: OrderState): Order
    suspend fun getFinishedOrdersHistory(restaurantId: String, page: Int, limit: Int): List<Order>
    suspend fun getCanceledOrdersHistory(restaurantId: String, page: Int, limit: Int): List<Order>
    suspend fun getOrdersRevenueByDaysBefore(
        restaurantId: String,
        daysBack: Int
    ): List<Pair<String, Double>>

    suspend fun getOrdersCountByDaysBefore(
        restaurantId: String,
        daysBack: Int
    ): List<Pair<String, Double>>
}

class ManageOrderUseCase(private val orderRemoteGateway: IOrderRemoteGateway) : IManageOrderUseCase {
    override suspend fun getCurrentOrders(restaurantId: String): List<Order> {
        return emptyList()
       // return orderRemoteGateway.getCurrentOrders(restaurantId)
    }

    override suspend fun updateOrderState(orderId: String, orderState: OrderState): Order {
        val newOrderState = when (orderState) {
            OrderState.PENDING -> Constant.IN_COOKING_ORDER
            OrderState.IN_COOKING -> Constant.FINISHED_ORDER
            OrderState.CANCELED -> Constant.CANCELED_ORDER
            else -> throw RequestException("")
        }
        return orderRemoteGateway.updateOrderState(orderId, newOrderState)
    }

    override suspend fun getFinishedOrdersHistory(
        restaurantId: String,
        page: Int,
        limit: Int
    ): List<Order> {
        return orderRemoteGateway.getOrdersHistory(restaurantId, page, limit)
            .filter { it.orderState == OrderState.FINISHED }
    }

    override suspend fun getCanceledOrdersHistory(
        restaurantId: String,
        page: Int,
        limit: Int
    ): List<Order> {
        return orderRemoteGateway.getOrdersHistory(restaurantId, page, limit)
            .filter { it.orderState == OrderState.CANCELED }
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
