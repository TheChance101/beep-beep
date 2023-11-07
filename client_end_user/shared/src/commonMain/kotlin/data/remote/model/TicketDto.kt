package data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TicketDto(
    @SerialName("id") val id: String? = null,
    @SerialName("userId") val userId: String,
    @SerialName("supportId") val supportId: String? = null,
    @SerialName("time") val openedAt: Long? = null,
    @SerialName("messages") val messages: List<MessageDto>? = null,
    @SerialName("isOpen") val isOpen: Boolean? = null
)