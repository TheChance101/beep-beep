package org.thechance.service_notification.domain.usecases

import org.koin.core.annotation.Single
import org.thechance.service_notification.domain.gateway.IDatabaseGateway
import org.thechance.service_notification.domain.gateway.INotificationGateway

@Single
class SendNotificationsUseCase(
    private val databaseGateway: IDatabaseGateway,
    private val notificationGateway: INotificationGateway
) : ISendNotificationsUseCase {

    override suspend fun sendNotificationToUser(userId: String, title: String, body: String) {
        val tokens = databaseGateway.getUserTokensById(userId)
        notificationGateway.sendNotification(tokens, title, body)
    }

}

interface ISendNotificationsUseCase {
    suspend fun sendNotificationToUser(userId: String, title: String, body: String)
}