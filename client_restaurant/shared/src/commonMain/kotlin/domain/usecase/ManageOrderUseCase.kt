package domain.usecase

import domain.entity.Order
import domain.gateway.IRemoteGateWay
import util.OrderState

interface IManageOrderUseCase {
    suspend fun getOrders(restaurantId: String): List<Order>
}

class ManageOrderUseCase(private val remoteGateWay: IRemoteGateWay) : IManageOrderUseCase {
    override suspend fun getOrders(restaurantId: String): List<Order> {
        return remoteGateWay.getCurrentOrders(restaurantId)
            .filter {
                it.orderStatus != OrderState.CANCELED.statusCode ||
                        it.orderStatus != OrderState.FINISHED.statusCode
            }
    }

}