package org.thechance.service_notification.domain.entity

data class Notification(
    val userId: String? = null,
    val topicId: String? = null,
    val title: String,
    val body: String,
    val sender: NotificationHistory.NotificationSender,
    val topic: String? = null
)