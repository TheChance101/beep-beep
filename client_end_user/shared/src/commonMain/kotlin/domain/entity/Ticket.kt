package domain.entity

data class Ticket(
    val id: String,
    val openedAt: Time,
    val messages : List<Message>
)