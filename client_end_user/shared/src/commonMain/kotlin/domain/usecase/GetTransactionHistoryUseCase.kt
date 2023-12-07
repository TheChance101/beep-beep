package domain.usecase

import domain.entity.FoodOrder
import domain.entity.NotificationHistory
import domain.entity.PaginationItems
import domain.entity.Trip
import domain.gateway.ITransactionsGateway
import domain.gateway.IUserGateway

interface IGetTransactionHistoryUseCase {
    suspend fun getOrdersHistory(page: Int, limit: Int): PaginationItems<FoodOrder>
    suspend fun getTripsHistory(page: Int, limit: Int): PaginationItems<Trip>
    suspend fun getNotificationHistory(page: Int, limit: Int): PaginationItems<NotificationHistory>
    suspend fun getNotificationHistoryInLast24Hours(): List<NotificationHistory>
}

class GetTransactionHistoryUseCase(
    private val userGateway: IUserGateway,
    private val transactionsGateway: ITransactionsGateway,
    ) : IGetTransactionHistoryUseCase {

    override suspend fun getOrdersHistory(page: Int, limit: Int): PaginationItems<FoodOrder> {
        return transactionsGateway.getOrderHistoryGateway(page, limit)
    }

    override suspend fun getTripsHistory(page: Int, limit: Int): PaginationItems<Trip> {
        return transactionsGateway.getTripHistory(page, limit)
    }

    override suspend fun getNotificationHistory(page: Int, limit: Int): PaginationItems<NotificationHistory> {
        return userGateway.getNotificationHistory(page, limit)
    }

    override suspend fun getNotificationHistoryInLast24Hours(): List<NotificationHistory> {
        return userGateway.getNotificationHistoryInLast24Hours()
    }
}
