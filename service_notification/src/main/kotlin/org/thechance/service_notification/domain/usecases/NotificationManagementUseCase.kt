package org.thechance.service_notification.domain.usecases

import org.koin.core.annotation.Single
import org.thechance.service_notification.domain.gateway.IPushNotificationGateway
import org.thechance.service_notification.domain.gateway.IDatabaseGateway
import org.thechance.service_notification.domain.model.Notification
import org.thechance.service_notification.domain.model.User

@Single
class NotificationManagementUseCase(
    private val databaseGateway: IDatabaseGateway,
    private val pushNotificationGateway: IPushNotificationGateway
) : INotificationManagementUseCase {

    override suspend fun sendNotificationToUser(userId: String, title: String, body: String) {
        val tokens = databaseGateway.getUserTokensById(userId)
        pushNotificationGateway.sendNotification(tokens, title, body)
        val notification = Notification(title, body, System.currentTimeMillis(), userId, false)
        databaseGateway.addNotificationToUserHistory(notification)
    }

    override suspend fun getUsers(): List<User> {
        return databaseGateway.getUsers()
    }

}

interface INotificationManagementUseCase {
    suspend fun sendNotificationToUser(userId: String, title: String, body: String)

    suspend fun getUsers(): List<User>

}