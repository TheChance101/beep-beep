package domain.usecase

import domain.entity.Order
import domain.gateway.IRemoteGateWay
import util.OrderState

interface IManageOrderUseCase {
    suspend fun getAllActiveOrders(restaurantId: String): List<Order>
    suspend fun getInCookingOrders(restaurantId: String): List<Order>
    suspend fun getPendingOrders(restaurantId: String): List<Order>
    suspend fun updateOrderState(orderId: String): Order
    suspend fun getFinishedOrdersHistory(restaurantId: String): List<Order>
    suspend fun getCanceledOrdersHistory(restaurantId: String): List<Order>
}

class ManageOrderUseCase(private val remoteGateWay: IRemoteGateWay) : IManageOrderUseCase {
    override suspend fun getAllActiveOrders(restaurantId: String): List<Order> {
        return remoteGateWay.getCurrentOrders(restaurantId)
    }

    override suspend fun getInCookingOrders(restaurantId: String): List<Order> {
        return remoteGateWay.getCurrentOrders(restaurantId)
            .filter { it.orderState == OrderState.IN_COOKING.statusCode }
    }

    override suspend fun getPendingOrders(restaurantId: String): List<Order> {
        return remoteGateWay.getCurrentOrders(restaurantId)
            .filter { it.orderState == OrderState.PENDING.statusCode }
    }

    override suspend fun updateOrderState(orderId: String): Order {

        val newOrderState = when (remoteGateWay.getOrderById(orderId)?.orderState) {
            0 -> OrderState.IN_COOKING.statusCode
            1 -> OrderState.FINISHED.statusCode
            else -> OrderState.CANCELED.statusCode
        }
        return remoteGateWay.updateOrderState(orderId, newOrderState)!!
    }

    override suspend fun getFinishedOrdersHistory(restaurantId: String): List<Order> {
        return remoteGateWay.getOrdersHistory(restaurantId)
            .filter { it.orderState == OrderState.FINISHED.statusCode }
    }

    override suspend fun getCanceledOrdersHistory(restaurantId: String): List<Order> {
        return remoteGateWay.getOrdersHistory(restaurantId)
            .filter { it.orderState == OrderState.FINISHED.statusCode }
    }

}