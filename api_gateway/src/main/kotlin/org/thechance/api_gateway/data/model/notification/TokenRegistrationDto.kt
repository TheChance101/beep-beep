package org.thechance.api_gateway.data.model.notification

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenRegistrationDto(
    @SerialName("userId") val userId: String,
    @SerialName("token") val token: String,
)
