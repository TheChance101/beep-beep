package org.thechance.service_notification.domain.usecases

import org.koin.core.annotation.Single
import org.thechance.service_notification.domain.entity.Notification
import org.thechance.service_notification.domain.gateway.IDatabaseGateway

@Single
class GetNotificationHistoryUseCase(private val databaseGateway: IDatabaseGateway) : IGetNotificationHistoryUseCase {
    override suspend operator fun invoke(page: Int, limit: Int): List<Notification> {
        return databaseGateway.getNotificationHistory(page, limit)
    }
}

interface IGetNotificationHistoryUseCase {

    suspend operator fun invoke(page: Int, limit: Int): List<Notification>

}