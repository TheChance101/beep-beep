package domain.usecase

import domain.entity.Order
import domain.gateway.IRemoteGateWay
import util.OrderState

interface IManageOrderUseCase {
    suspend fun getAllActiveOrders(restaurantId: String): List<Order>
    suspend fun getInCookingOrders(restaurantId: String): List<Order>
    suspend fun getPendingOrders(restaurantId: String): List<Order>
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

}