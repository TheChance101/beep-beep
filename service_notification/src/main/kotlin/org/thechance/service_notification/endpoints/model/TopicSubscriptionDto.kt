package org.thechance.service_notification.endpoints.model

import kotlinx.serialization.Serializable

@Serializable
data class TopicSubscriptionDto(
    val topic: String,
    val token: String,
)
