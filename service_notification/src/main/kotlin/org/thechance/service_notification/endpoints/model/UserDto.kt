package org.thechance.service_notification.endpoints.model

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: String,
    val deviceTokens: List<String>,
    val topics: List<String>,
    val notifications: List<NotificationDto>
)
