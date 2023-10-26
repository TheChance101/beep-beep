package domain.entity

import kotlinx.datetime.LocalDate

data class Notification(
    val id: String,
    val title: String,
    val body: String,
    val date: LocalDate,
    val time: Time,
    val userId: String,
    val topic: String,
)
