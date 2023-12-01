package org.thechance.service_notification.domain.gateway

import org.thechance.service_notification.domain.entity.NotificationHistory

interface IDatabaseGateway {

    suspend fun deleteAllNotification(): Boolean
    suspend fun getUserTokens(userId: String): List<String>
    suspend fun clearDeviceToken(userId: String ,deviceToken:String):Boolean
    suspend fun clearAllDeviceUserTokens(userId: String):Boolean

    suspend fun addNotificationToHistory(notificationHistory: NotificationHistory)

    suspend fun getNotificationHistoryForUser(page: Int, limit: Int): List<NotificationHistory>

    suspend fun getNotificationHistoryForUser(page: Int, limit: Int, userId: String): List<NotificationHistory>

    suspend fun getNotificationHistoryInTheLast24Hours(userId: String): List<NotificationHistory>

    suspend fun getTotalCountsOfNotificationHistoryForUser(userId: String): Long

    suspend fun registerToken(userId: String, token: String): Boolean

    suspend fun getUsersTokens(ids: List<String>): List<String>

    suspend fun createTopic(name: String): Boolean

    suspend fun getTopics(): List<String>

    suspend fun isTopicAlreadyExists(name: String): Boolean

}