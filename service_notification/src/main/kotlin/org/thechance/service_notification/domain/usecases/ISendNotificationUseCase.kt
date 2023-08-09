package org.thechance.service_notification.domain.usecases


interface ISendNotificationUseCase {
    suspend fun sendNotification(tokens: List<String>, title: String, body: String)
}