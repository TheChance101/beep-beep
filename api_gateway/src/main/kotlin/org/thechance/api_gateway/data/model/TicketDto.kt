package org.thechance.api_gateway.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class TicketDto(
    @SerialName("id") val id: String? = null,
    @SerialName("userId") val userId: String,
    @SerialName("supportId") val supportId: String? = null,
    @SerialName("time") val time: Long? = null,
    @SerialName("messages") val messages: List<MessageDto>? = null
)