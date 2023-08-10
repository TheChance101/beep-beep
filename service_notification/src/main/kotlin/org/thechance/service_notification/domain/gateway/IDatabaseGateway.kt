package org.thechance.service_notification.domain.gateway

import org.thechance.service_notification.domain.model.Notification
import org.thechance.service_notification.domain.model.NotificationRequest
import org.thechance.service_notification.domain.model.User

interface IDatabaseGateway {
    suspend fun getNotificationByUserId(id: String): Notification

    suspend fun getTokensForUserById(id: String): List<String>

    suspend fun addNotificationToUserHistory(notification: NotificationRequest)

    suspend fun getNotificationHistory(page: Int, limit: Int): List<Notification>

    suspend fun registerToken(userId: String, token: String): Boolean

    suspend fun getUsersGroupIds(userGroup: String): List<String>

    suspend fun getUsersTokens(ids: List<String>): List<String>

    suspend fun addUserToGroup(userId: String, userGroup: String): Boolean

}