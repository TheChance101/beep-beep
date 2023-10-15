package data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessageDto(
    @SerialName("id") val id: String? = null,
    @SerialName("ticketId") val ticketId: String? = null,
    @SerialName("message") val message: String? = null,
    @SerialName("senderId") val senderId: String? = null,
    @SerialName("senderAvatar") val senderAvatar: String? = null,
)