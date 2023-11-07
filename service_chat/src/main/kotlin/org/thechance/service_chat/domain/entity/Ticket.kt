package org.thechance.service_chat.domain.entity

data class Ticket(
    val id: String,
    val userId: String,
    val supportId: String,
    val time: Long,
    val messages: List<Message>,
    val isOpen: Boolean = true
) {
    fun generateTicketId(): String {
        val randomString = (1..8)
            .map { (('A'..'Z') + ('a'..'z') + ('0'..'9')).random() }
            .joinToString("")

        return "Beep-$randomString"
    }
}