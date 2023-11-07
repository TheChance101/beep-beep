package data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class NotificationHistoryDto(
    val id: String? = null,
    val topicId: String? = null,
    val sender: Int,
    val title: String,
    val body: String,
    val date: Long,
    val userId: String? = null,
    val topic: String? = null
)