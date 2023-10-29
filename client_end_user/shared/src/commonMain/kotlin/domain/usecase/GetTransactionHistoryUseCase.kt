package domain.usecase

import domain.entity.FoodOrder
import domain.entity.Trip
import domain.gateway.ITransactionsGateway

interface IGetTransactionHistoryUseCase {
    suspend fun getOrdersHistory(): List<FoodOrder>
    suspend fun getTripsHistory(): List<Trip>
}

class GetTransactionHistoryUseCase(
    private val transactionsGateway: ITransactionsGateway
) : IGetTransactionHistoryUseCase {

    override suspend fun getOrdersHistory(): List<FoodOrder> {
        return transactionsGateway.getOrderHistoryGateway()
            .filter { it.orderStatus.statusCode == FINISHED_ORDER }
    }

    override suspend fun getTripsHistory(): List<Trip> {
        return transactionsGateway.getTripHistory()
    }

    companion object {
        private const val FINISHED_ORDER = 4
    }
}
