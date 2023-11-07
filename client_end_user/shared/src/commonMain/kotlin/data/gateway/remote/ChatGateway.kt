package data.gateway.remote

import domain.entity.Message
import domain.entity.Ticket
import domain.gateway.IChatGateway
import kotlinx.coroutines.flow.Flow

class ChatGateway : IChatGateway {
    override fun getTickets(): Flow<Ticket> {
        TODO("Not yet implemented")
    }

    override fun getMessages(ticketId: String): Flow<List<Message>> {
        TODO("Not yet implemented")
    }

    override suspend fun sendMessage(message: String, senderId: String, ticketId: String) {
        TODO("Not yet implemented")
    }
}