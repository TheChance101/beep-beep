package org.thechance.common.domain.entity

data class NewMessage(
    val ticketId: String,
    val senderId: String,
    val message: String,
)
