package org.thechance.service_notification.domain.gateway

import org.thechance.service_notification.domain.model.Notification
import org.thechance.service_notification.domain.model.NotificationRequest
import org.thechance.service_notification.domain.model.User

interface IDatabaseGateway {
    suspend fun createUser(user: User): Boolean
    suspend fun addTokenToUser(id: String, token: String): Boolean

    suspend fun getTokensForUserById(id: String): List<String>

    suspend fun addNotificationToUserHistory(notification: NotificationRequest)

    suspend fun getNotificationHistory(page: Int, limit: Int): List<Notification>

}