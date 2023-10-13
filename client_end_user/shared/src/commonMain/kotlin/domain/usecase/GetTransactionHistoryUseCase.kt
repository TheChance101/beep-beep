package domain.usecase

import domain.entity.Order
import domain.entity.Trip
import domain.gateway.IOrderGateway

interface IGetTransactionHistoryUseCase {
    suspend fun getOrdersHistory(): List<Order>
    suspend fun getTripsHistory(): List<Trip>
}

class GetTransactionHistoryUseCase(
    private val orderGateway: IOrderGateway
) : IGetTransactionHistoryUseCase {
    override suspend fun getOrdersHistory(): List<Order> {
        return orderGateway.getOrderHistoryGateway()
            .filter { it.orderStatus == FINISHED_ORDER }
    }

    override suspend fun getTripsHistory(): List<Trip> {
        return orderGateway.getTripHistory()
    }

    companion object {
        private const val FINISHED_ORDER = 4
    }
}
