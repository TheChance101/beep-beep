package domain.entity

import kotlinx.datetime.LocalDate

data class NotificationHistory(
    val id: String,
    val topicId: String,
    val title: String,
    val body: String,
    val date: LocalDate,
    val time: Time,
    val userId: String,
    val topic: String,
)