package org.thechance.service_notification.endpoints.model

import kotlinx.serialization.Serializable

@Serializable
data class NotificationDto(
    val title: String,
    val body: String,
    val date: Long,
    val userId : String,
    val isRead : Boolean
)