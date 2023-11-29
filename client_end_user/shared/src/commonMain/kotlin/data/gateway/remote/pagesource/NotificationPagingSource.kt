package data.gateway.remote.pagesource

import domain.entity.NotificationHistory
import domain.entity.PaginationItems
import domain.gateway.IUserGateway
import domain.usecase.IGetTransactionHistoryUseCase
import org.koin.core.component.KoinComponent

class NotificationPagingSource(
    private val transaction: IGetTransactionHistoryUseCase,
    ) : BasePagingSource<NotificationHistory>(), KoinComponent {
    override suspend fun fetchData(page: Int, limit: Int): PaginationItems<NotificationHistory> {
        return transaction.getNotificationHistory(page, limit)
    }
}