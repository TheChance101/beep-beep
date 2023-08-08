package org.thechance.service_notification.domain.gateway

interface INotificationGateway {

    suspend fun sendNotification(userTokens: List<String>, title: String, body: String)

}