package org.thechance.service_chat.endpoints.models

import io.ktor.server.websocket.*
import org.thechance.service_chat.domain.entity.Ticket
import java.util.*

data class SupportAgent(val session: WebSocketServerSession) {
    val tickets: Queue<Ticket> = LinkedList()
}