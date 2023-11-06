package org.thechance.service_notification.domain.usecases

import org.koin.core.annotation.Single
import org.thechance.service_notification.data.mappers.toNotificationHistory
import org.thechance.service_notification.domain.entity.InternalServerErrorException
import org.thechance.service_notification.domain.entity.NotFoundException
import org.thechance.service_notification.domain.entity.Notification
import org.thechance.service_notification.domain.entity.NotificationHistory
import org.thechance.service_notification.domain.gateway.IDatabaseGateway
import org.thechance.service_notification.domain.gateway.IPushNotificationGateway
import org.thechance.service_notification.endpoints.MISSING_PARAMETER
import org.thechance.service_notification.endpoints.NOTIFICATION_NOT_SENT
import org.thechance.service_notification.endpoints.TOPIC_NOT_EXISTS

interface INotificationManagementUseCase {
    suspend fun sendNotificationToUser(notification: Notification): Boolean

    suspend fun sendNotificationToTopic(notification: Notification): Boolean

    suspend fun getNotificationHistory(page: Int, limit: Int): List<NotificationHistory>

    suspend fun getTotalCountsOfNotificationHistoryForUser(userId: String): Long

    suspend fun getNotificationHistoryForUser(page: Int, limit: Int, userId: String): List<NotificationHistory>

    suspend fun getNotificationHistoryInTheLast24Hours(userId: String): List<NotificationHistory>
}

@Single
class NotificationManagementUseCase(
    private val pushNotificationGateway: IPushNotificationGateway,
    private val databaseGateway: IDatabaseGateway,
) : INotificationManagementUseCase {

    override suspend fun sendNotificationToUser(notification: Notification): Boolean {
        val tokens = databaseGateway.getUserTokens(notification.userId ?: throw NotFoundException(MISSING_PARAMETER))
        return pushNotificationGateway.sendNotification(tokens, notification.title, notification.body).also {
            if (it) {
                databaseGateway.addNotificationToHistory(notification.toNotificationHistory())
            } else {
                throw InternalServerErrorException(NOTIFICATION_NOT_SENT)
            }
        }
    }

    override suspend fun sendNotificationToTopic(notification: Notification): Boolean {
        if (!databaseGateway.isTopicAlreadyExists(notification.topic ?: throw NotFoundException(MISSING_PARAMETER))) {
            throw NotFoundException(TOPIC_NOT_EXISTS)
        }
        return pushNotificationGateway.sendNotificationToTopic(
            notification.topic,
            notification.title,
            notification.body
        ).also {
            databaseGateway.addNotificationToHistory(notification.toNotificationHistory())
        }
    }

    override suspend fun getNotificationHistory(page: Int, limit: Int): List<NotificationHistory> {
        return databaseGateway.getNotificationHistoryForUser(page, limit)
    }

    override suspend fun getTotalCountsOfNotificationHistoryForUser(userId: String): Long {
        return databaseGateway.getTotalCountsOfNotificationHistoryForUser(userId)
    }

    override suspend fun getNotificationHistoryForUser(
        page: Int,
        limit: Int,
        userId: String
    ): List<NotificationHistory> {
        return databaseGateway.getNotificationHistoryForUser(page = page, limit = limit, userId = userId)
    }

    override suspend fun getNotificationHistoryInTheLast24Hours(userId: String): List<NotificationHistory> {
        return databaseGateway.getNotificationHistoryInTheLast24Hours(userId)
    }
}