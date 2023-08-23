package domain.usecase

import domain.entity.Order
import domain.entity.OrderState
import domain.gateway.IRemoteGateWay

interface IManageOrderUseCase {
    suspend fun getInCookingOrders(restaurantId: String): List<Order>
    suspend fun getPendingOrders(restaurantId: String): List<Order>
    suspend fun updateOrderState(orderId: String): Order?
}

class ManageOrderUseCase(private val remoteGateWay: IRemoteGateWay) : IManageOrderUseCase {

    override suspend fun getInCookingOrders(restaurantId: String): List<Order> {
        return remoteGateWay.getCurrentOrders(restaurantId)
            .filter { it.orderState.statusCode == OrderState.IN_COOKING.statusCode }
    }

    override suspend fun getPendingOrders(restaurantId: String): List<Order> {
        return remoteGateWay.getCurrentOrders(restaurantId)
            .filter { it.orderState.statusCode == OrderState.PENDING.statusCode }
    }

    override suspend fun updateOrderState(orderId: String): Order? {

        val newOrderState = when (remoteGateWay.getOrderById(orderId)?.orderState) {
            OrderState.PENDING -> OrderState.IN_COOKING.statusCode
            OrderState.IN_COOKING -> OrderState.FINISHED.statusCode
            OrderState.CANCELED -> OrderState.CANCELED.statusCode
            OrderState.FINISHED -> OrderState.FINISHED.statusCode
            null -> OrderState.IN_COOKING.statusCode
        }
        return remoteGateWay.updateOrderState(orderId, newOrderState)
    }

}