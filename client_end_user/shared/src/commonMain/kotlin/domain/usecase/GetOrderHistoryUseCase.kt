package domain.usecase

import domain.entity.Order
import domain.entity.Trip
import domain.gateway.IOrderRemoteGateway

interface IGetOrderHistoryUseCase {
    suspend fun getOrdersHistory(): List<Order>
    suspend fun getTripsHistory(): List<Trip>
}

class GetOrderHistoryUseCase(
    private val orderRemoteGateway: IOrderRemoteGateway
) : IGetOrderHistoryUseCase {
    override suspend fun getOrdersHistory(): List<Order> {
        return orderRemoteGateway.getOrderHistoryGateway()
            .filter { it.orderStatus == FINISHED_ORDER }
    }

    override suspend fun getTripsHistory(): List<Trip> {
        return orderRemoteGateway.getTripHistory()
    }

    companion object {
        private const val FINISHED_ORDER = 4
    }
}
