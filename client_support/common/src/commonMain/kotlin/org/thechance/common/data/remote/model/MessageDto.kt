package org.thechance.common.data.remote.model

data class MessageDto(
    val id: String? = null,
    val ticketId: String? = null,
    val message: String? = null,
    val senderId: String? = null,
    val senderAvatar: String? = null,
)
