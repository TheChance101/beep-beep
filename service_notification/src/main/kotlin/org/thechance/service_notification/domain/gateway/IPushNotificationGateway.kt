package org.thechance.service_notification.domain.gateway

interface IPushNotificationGateway {

    suspend fun sendNotification(userTokens: List<String>, title: String, body: String): Boolean

    suspend fun sendNotificationToTopic(topic: String, title: String, body: String): Boolean

    suspend fun subscribeTokenToTopic(topicName: String, token: String): Boolean

    suspend fun unsubscribeTokenFromTopic(topicName: String, token: String): Boolean

}