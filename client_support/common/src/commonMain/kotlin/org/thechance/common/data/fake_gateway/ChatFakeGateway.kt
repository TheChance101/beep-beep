package org.thechance.common.data.fake_gateway

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.sample
import org.thechance.common.data.mapper.toDto
import org.thechance.common.data.mapper.toEntity
import org.thechance.common.data.remote.model.MessageDto
import org.thechance.common.data.remote.model.TicketDto
import org.thechance.common.domain.entity.Message
import org.thechance.common.domain.entity.NewMessage
import org.thechance.common.domain.entity.Ticket
import org.thechance.common.domain.gateway.IChatGateway
import kotlin.random.Random

class ChatFakeGateway : IChatGateway {

    private val tickets = MutableStateFlow(
        listOf(
            TicketDto(
                id = "ABC123",
                username = "John Doe",
                avatar = "",
                openedAt = "2:30 PM",
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
        )
    )

    @OptIn(FlowPreview::class)
    override fun getTickets(): Flow<Ticket?> {
        return tickets.sample(1500).map { it.firstOrNull()?.toEntity() }
    }

    override suspend fun closeTicket(ticketId: String) {
        tickets.value -= tickets.value.first { it.id == ticketId }
    }

    override fun getMessages(ticketId: String): Flow<List<Message>> {
        return messages.map { it.filter { it.ticketId == ticketId }.map { it.toEntity() } }
    }

    override suspend fun sendMessage(message: NewMessage) {
        messages.value += message.toDto()
        delay(1000)
        answerWithFakeMessage(message)
    }

    private fun answerWithFakeMessage(message: NewMessage) {
        val fakeAnswers = listOf(
            "Ok, thank you for your response.",
            "I will check it.",
            "I will get back to you soon.",
            "That's great.",
            "Seems like a good idea.",
        )
        messages.value += MessageDto(
            id = Random.nextInt().toString(),
            ticketId = message.ticketId,
            message = fakeAnswers.random(),
            senderId = (Random.nextInt(2, 1000)).toString(),
            senderAvatar = ""
        )
    }
}