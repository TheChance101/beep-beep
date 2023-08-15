package org.thechance.service_notification.domain.usecases

import org.koin.core.annotation.Single
import org.thechance.service_notification.domain.entity.NotFoundException
import org.thechance.service_notification.domain.entity.Notification
import org.thechance.service_notification.domain.gateway.IDatabaseGateway
import org.thechance.service_notification.domain.gateway.IPushNotificationGateway
import org.thechance.service_notification.endpoints.TOPIC_NOT_EXISTS

interface INotificationManagementUseCase {
    suspend fun sendNotificationToUser(userId: String, title: String, body: String): Boolean

    suspend fun sendNotificationToTopic(topic: String, title: String, body: String): Boolean

    suspend fun getNotificationHistory(page: Int, limit: Int): List<Notification>
}

@Single
class NotificationManagementUseCase(
    private val pushNotificationGateway: IPushNotificationGateway,
    private val databaseGateway: IDatabaseGateway,
) : INotificationManagementUseCase {

    override suspend fun sendNotificationToUser(userId: String, title: String, body: String): Boolean {
        val tokens = databaseGateway.getUserTokens(userId)
        return pushNotificationGateway.sendNotification(tokens, title, body).also {
            databaseGateway.addNotificationToHistory(
                Notification(
                    title = title,
                    body = body,
                    date = System.currentTimeMillis(),
                    userId = userId
                )
            )
        }
    }

    override suspend fun sendNotificationToTopic(topic: String, title: String, body: String): Boolean {
        if (!databaseGateway.isTopicAlreadyExists(topic)) throw NotFoundException(TOPIC_NOT_EXISTS)
        return pushNotificationGateway.sendNotificationToTopic(topic, title, body).also {
            databaseGateway.addNotificationToHistory(
                Notification(
                    title = title,
                    body = body,
                    date = System.currentTimeMillis(),
                    topic = topic
                )
            )
        }
    }

    override suspend fun getNotificationHistory(page: Int, limit: Int): List<Notification> {
        return databaseGateway.getNotificationHistory(page, limit)
    }

}