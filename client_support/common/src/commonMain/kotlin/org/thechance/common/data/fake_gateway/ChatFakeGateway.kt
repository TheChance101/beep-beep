package org.thechance.common.data.fake_gateway

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import org.thechance.common.data.mapper.toDto
import org.thechance.common.data.mapper.toEntity
import org.thechance.common.data.remote.model.MessageDto
import org.thechance.common.data.remote.model.TicketDto
import org.thechance.common.domain.entity.Message
import org.thechance.common.domain.entity.NewMessage
import org.thechance.common.domain.entity.Ticket
import org.thechance.common.domain.gateway.IChatGateway

class ChatFakeGateway : IChatGateway {

    private val tickets = MutableStateFlow(
        listOf(
            TicketDto(
                id = "ABC123",
                username = "John Doe",
                avatar = "",
                openedAt = "2:30 PM",
            ),
            TicketDto(
                id = "ABC124",
                username = "Mary Doe",
                avatar = "",
                openedAt = "3:55 PM",
            ),
            TicketDto(
                id = "ABC125",
                username = "Alex Doe",
                avatar = "",
                openedAt = "5:12 PM",
            ),
        )
    )
    private val messages = MutableStateFlow(
        listOf(
            MessageDto(
                id = "1",
                ticketId = "ABC123",
                message = "Hello, I have a problem with my order.",
                senderId = "123",
                senderAvatar = ""
            ),
            MessageDto(
                id = "2",
                ticketId = "ABC124",
                message = "Good morning, I have forgotten my password.",
                senderId = "124",
                senderAvatar = ""
            ),
            MessageDto(
                id = "3",
                ticketId = "ABC125",
                message = "Could you please help me?, I can't login.",
                senderId = "125",
                senderAvatar = ""
            ),
        )
    )

    override fun getTickets(): Flow<Ticket> {
        return tickets.map { it.first().toEntity() }
    }

    override suspend fun closeTicket(ticketId: String) {
        tickets.value -= tickets.value.first { it.id == ticketId }
    }

    override fun getMessages(ticketId: String): Flow<List<Message>> {
        return messages.map { it.filter { it.ticketId == ticketId }.map { it.toEntity() } }
    }

    override suspend fun sendMessage(message: NewMessage) {
        messages.value += message.toDto()
    }
}