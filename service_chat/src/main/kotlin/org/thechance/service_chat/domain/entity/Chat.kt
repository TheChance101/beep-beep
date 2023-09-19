package org.thechance.service_chat.domain.entity

data class Chat(
    val ticketId: String,
    val userId: String,
    val supportId: String,
    val messages: List<Message>
) {
    fun generateTicketId(): String {
        val randomString = (1..6)
            .map { (('A'..'Z') + ('a'..'z') + ('0'..'9')).random() }
            .joinToString("")

        return "Bp$randomString"
    }
}