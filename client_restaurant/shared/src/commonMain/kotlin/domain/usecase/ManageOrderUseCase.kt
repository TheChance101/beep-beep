package domain.usecase

import domain.entity.Order
import domain.entity.OrderState
import domain.gateway.IRemoteGateWay
import presentation.order.OrderStateType

interface IManageOrderUseCase {
    suspend fun getInCookingOrders(restaurantId: String): List<Order>
    suspend fun getPendingOrders(restaurantId: String): List<Order>
    suspend fun updateOrderState(orderId: String, orderState: OrderStateType): Order
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

    override suspend fun updateOrderState(orderId: String, orderState: OrderStateType): Order {
        val newOrderState = when (orderState) {
            OrderStateType.APPROVE -> OrderState.IN_COOKING.statusCode
            OrderStateType.CANCEL -> OrderState.CANCELED.statusCode
            OrderStateType.FINISH -> OrderState.FINISHED.statusCode
        }
        return remoteGateWay.updateOrderState(orderId, newOrderState)
    }

}