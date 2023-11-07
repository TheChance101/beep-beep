package domain.usecase

import domain.entity.Message
import domain.entity.Ticket
import domain.gateway.IChatGateway
import kotlinx.coroutines.flow.Flow

interface IChatUseCase {
    fun getMessages(ticketId: String): Flow<List<Message>>

    suspend fun sendMessage(message: String, senderId: String, ticketId: String)

    suspend fun getTickets(): Flow<Ticket>

}

class ChatUseCase(private val chatGateway: IChatGateway) : IChatUseCase {
    override fun getMessages(ticketId: String): Flow<List<Message>> {
        return chatGateway.getMessages(ticketId)
    }

    override suspend fun sendMessage(message: String, senderId: String, ticketId: String) {
        chatGateway.sendMessage(message, senderId, ticketId)
    }

    override suspend fun getTickets(): Flow<Ticket> {
        return chatGateway.getTickets()
    }
}
