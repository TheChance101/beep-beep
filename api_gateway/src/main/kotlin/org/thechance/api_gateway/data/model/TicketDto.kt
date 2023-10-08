package org.thechance.api_gateway.data.model

import kotlinx.serialization.Serializable


@Serializable
data class TicketDto(
    val id: String? = null,
    val userId: String? = null,
    val supportId: String ? = null,
    val time : Long? = null,
    val messages: List<MessageDto>? = null
)