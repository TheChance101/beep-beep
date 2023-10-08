package org.thechance.api_gateway.data.model

import kotlinx.serialization.Serializable

@Serializable
data class MessageDto(
    val id : String? = null,
    val senderId : String? = null,
    val content : String? = null,
    val time : Long? = null
)