package domain.usecase

import domain.entity.Order
import domain.gateway.IRemoteGateWay
import util.OrderState

interface IGetNewOrdersUseCase {
    //    suspend operator fun invoke(restaurantId: String): List<Order>
    suspend fun getOrders(restaurantId: String): List<Order>
}

class GetNewOrdersUseCase(private val remoteGateWay: IRemoteGateWay) : IGetNewOrdersUseCase {
    //    override suspend fun invoke(restaurantId: String): List<Order> {
//        println("LLLLLLLLLLLLL ${remoteGateWay.getCurrentOrders(restaurantId)}")
//        return remoteGateWay.getCurrentOrders(restaurantId)
////            .filter {
////                it.orderStatus != OrderState.CANCELED.statusCode ||
////                        it.orderStatus != OrderState.DONE.statusCode
////            }
//    }
    override suspend fun getOrders(restaurantId: String): List<Order> {
        return remoteGateWay.getCurrentOrders(restaurantId)
    }

}