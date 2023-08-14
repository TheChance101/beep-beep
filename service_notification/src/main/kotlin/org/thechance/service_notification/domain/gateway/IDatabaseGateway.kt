package org.thechance.service_notification.domain.gateway

import org.thechance.service_notification.domain.entity.Notification

interface IDatabaseGateway {

    suspend fun getUserTokens(id: String): List<String>

    suspend fun addNotificationToHistory(notification: Notification)

    suspend fun getNotificationHistory(page: Int, limit: Int): List<Notification>

    suspend fun registerToken(userId: String, token: String): Boolean

    suspend fun getUsersTokens(ids: List<String>): List<String>

    suspend fun createTopic(name: String): Boolean

    suspend fun getTopics(): List<String>

    suspend fun isTopicAlreadyExists(name: String): Boolean

}