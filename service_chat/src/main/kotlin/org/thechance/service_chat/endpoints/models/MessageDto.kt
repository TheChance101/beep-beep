package org.thechance.service_chat.endpoints.models

import kotlinx.serialization.Serializable

@Serializable
data class MessageDto(
    val id : String? = null,
    val senderId : String? = null,
    val content : String? = null,
    val time : Long? = null
)