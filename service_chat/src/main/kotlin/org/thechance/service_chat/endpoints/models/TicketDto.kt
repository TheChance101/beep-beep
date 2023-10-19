package org.thechance.service_chat.endpoints.models

import kotlinx.serialization.Serializable


@Serializable
data class TicketDto(
    val id: String? = null,
    val userId: String,
    val supportId: String? = null,
    val time: Long? = null,
    val messages: List<MessageDto>? = null,
    val isOpen: Boolean? = null
)