package org.thechance.api_gateway.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessageDto(
    @SerialName("id") val id: String? = null,
    @SerialName("senderId") val senderId: String? = null,
    @SerialName("content") val content: String? = null,
    @SerialName("time") val time: Long? = null
)