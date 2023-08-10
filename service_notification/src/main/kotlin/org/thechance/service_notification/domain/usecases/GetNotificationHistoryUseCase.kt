package org.thechance.service_notification.domain.usecases

import org.koin.core.annotation.Single
import org.thechance.service_notification.domain.gateway.IDatabaseGateway
import org.thechance.service_notification.domain.model.Notification
import org.thechance.service_notification.domain.model.User

@Single
class GetNotificationHistoryUseCase(private val databaseGateway: IDatabaseGateway) : IGetNotificationHistoryUseCase {

    override suspend fun getNotificationsHistory(page: Int, limit: Int): List<Notification> {
        return databaseGateway.getNotificationHistory(page, limit)
    }

}

interface IGetNotificationHistoryUseCase {

    suspend fun getNotificationsHistory(page: Int, limit: Int) : List<Notification>

}