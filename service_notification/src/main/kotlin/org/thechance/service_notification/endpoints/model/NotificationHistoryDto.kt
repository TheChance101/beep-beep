package org.thechance.service_notification.endpoints.model

import kotlinx.serialization.Serializable

@Serializable
data class NotificationHistoryDto(
    val id: String? = null,
    val title: String,
    val body: String,
    val date: Long,
    val userId: String? = null,
    val topic: String? = null,
)