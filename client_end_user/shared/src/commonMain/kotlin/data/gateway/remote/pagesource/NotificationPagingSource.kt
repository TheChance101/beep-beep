package data.gateway.remote.pagesource

import domain.entity.NotificationHistory
import domain.entity.PaginationItems
import domain.gateway.IUserGateway
import org.koin.core.component.KoinComponent

class NotificationPagingSource(
    private val userGateway: IUserGateway,
) : BasePagingSource<NotificationHistory>(), KoinComponent {
    override suspend fun fetchData(page: Int, limit: Int): PaginationItems<NotificationHistory> {
        return userGateway.getNotificationHistory(page, limit)
    }
}