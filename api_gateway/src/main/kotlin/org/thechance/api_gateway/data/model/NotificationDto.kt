package org.thechance.api_gateway.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NotificationDto(
    @SerialName("title") val title: String,
    @SerialName("body") val body: String,
)
