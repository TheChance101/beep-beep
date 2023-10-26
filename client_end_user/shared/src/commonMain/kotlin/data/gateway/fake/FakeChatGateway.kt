package data.gateway.fake

import data.remote.mapper.toDto
import data.remote.mapper.toEntity
import data.remote.model.MessageDto
import data.remote.model.TicketDto
import domain.entity.Message
import domain.entity.Ticket
import domain.gateway.IChatGateway
import domain.utils.GeneralException
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.sample
import kotlin.random.Random

class FakeChatGateway : IChatGateway {
    @OptIn(FlowPreview::class)
    override fun getTickets(): Flow<Ticket> {
        return tickets.sample(1500)
            .map { it.firstOrNull()?.toEntity() ?: throw GeneralException.NotFoundException }
    }

    override fun getMessages(ticketId: String): Flow<List<Message>> {
        return messages.map {
            it.filter { messageDto ->
                messageDto.ticketId == ticketId
            }.map { filteredMessage -> filteredMessage.toEntity() }
        }
    }

    override suspend fun sendMessage(message: String, senderId: String, ticketId: String) {
        val newMessage = Message(
            id = "123",
            ticketId = ticketId,
            senderId = senderId,
            message = message,
            isMe = true,
            avatarUrl = ""
        )
        messages.value += newMessage.toDto()
        delay(1500)
        answerWithFakeMessage(newMessage)
    }

    private fun answerWithFakeMessage(message: Message) {
        val fakeAnswers = listOf(
            "Ok, I will check it.",
            "I will get back to you soon.",
            "Issue resolved",
        )
        messages.value += MessageDto(
            id = "message$index",
            ticketId = message.ticketId,
            message = fakeAnswers[index],
            senderId = (Random.nextInt(2, 1000)).toString(),
            senderAvatar = ""
        )
        index++
    }

    private val tickets = MutableStateFlow(
        listOf(
            TicketDto(
                id = "ABC123",
                username = "John Doe",
                avatar = "",
                openedAt = "14:30",
            ),
        )
    )

    private val messages = MutableStateFlow(listOf<MessageDto>())
    private var index = 0
}