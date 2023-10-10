package domain.entity

data class Ticket(
    val id: String,
    val username: String,
    val avatar: String,
    val openedAt: Time,
)