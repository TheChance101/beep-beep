package domain.usecase

import domain.entity.Order
import domain.entity.OrderState
import domain.gateway.IFakeRemoteGateway
import domain.utils.Constant
import presentation.base.RequestException

interface IManageOrderUseCase {
    suspend fun getCurrentOrders(): List<Order>
    suspend fun updateOrderState(orderId: String, orderState: OrderState): Order
    suspend fun getFinishedOrdersHistory(restaurantId: String): List<Order>
    suspend fun getCanceledOrdersHistory(restaurantId: String): List<Order>
}

class ManageOrderUseCase(private val remoteGateWay: IFakeRemoteGateway) : IManageOrderUseCase {
    override suspend fun getCurrentOrders(): List<Order> {
        return remoteGateWay.getCurrentOrders()
    }
    override suspend fun updateOrderState(orderId: String, orderState: OrderState): Order {
        val newOrderState = when (orderState) {
            OrderState.PENDING -> Constant.IN_COOKING_ORDER
            OrderState.IN_COOKING -> Constant.FINISHED_ORDER
            OrderState.CANCELED -> Constant.CANCELED_ORDER
            else -> throw RequestException()
        }
        return remoteGateWay.updateOrderState(orderId, newOrderState)
    }

    override suspend fun getFinishedOrdersHistory(restaurantId: String): List<Order> {
        return remoteGateWay.getOrdersHistory(restaurantId)
            .filter { it.orderState == OrderState.FINISHED }
    }

    override suspend fun getCanceledOrdersHistory(restaurantId: String): List<Order> {
        return remoteGateWay.getOrdersHistory(restaurantId)
            .filter { it.orderState == OrderState.CANCELED }
    }

}
