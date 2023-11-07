package org.thechance.api_gateway.data.model.notification

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NotificationDto(
    @SerialName("userId")val userId: String? = null,
    @SerialName("topicId") val topicId: String? = null, // could be orderId , taxi ride id , delivery trip id
    @SerialName("title") val title: String,
    @SerialName("body") val body: String,
    @SerialName("sender")val sender: Int,
    @SerialName("topic")val topic: String? = null
)