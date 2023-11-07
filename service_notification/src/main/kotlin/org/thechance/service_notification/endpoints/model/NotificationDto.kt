package org.thechance.service_notification.endpoints.model

import kotlinx.serialization.Serializable

@Serializable
data class NotificationDto(
    val userId: String? = null,
    val topicId: String? = null,
    val title: String,
    val body: String,
    val sender: Int,
    val topic: String? = null
)
