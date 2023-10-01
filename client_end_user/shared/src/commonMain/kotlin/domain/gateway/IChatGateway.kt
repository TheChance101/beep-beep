package domain.gateway

import domain.entity.Message
import domain.entity.Ticket
import kotlinx.coroutines.flow.Flow

interface IChatGateway {
    fun getTickets(): Flow<Ticket>
    fun getMessages(ticketId: String): Flow<List<Message>>
    suspend fun sendMessage(message: String, senderId: String, ticketId: String)
}