package org.thechance.service_notification.endpoints.model

import kotlinx.serialization.Serializable

@Serializable
data class TokenRegistrationDto(
    val userId: String,
    val token: String,
)
