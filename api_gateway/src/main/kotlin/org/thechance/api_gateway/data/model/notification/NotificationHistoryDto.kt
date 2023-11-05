package org.thechance.api_gateway.data.model.notification

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NotificationHistoryDto(
    @SerialName("id") val id: String? = null,
    @SerialName("title") val title: String,
    @SerialName("body") val body: String,
    @SerialName("date") val date: Long,
    @SerialName("userId") val userId: String? = null,
    @SerialName("topic") val topic: String? = null,
)