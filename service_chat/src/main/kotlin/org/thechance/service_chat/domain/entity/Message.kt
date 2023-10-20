package org.thechance.service_chat.domain.entity

data class Message(
    val id : String,
    val senderId : String,
    val content : String,
    val time : Long
)