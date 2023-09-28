package domain.usecase

import domain.entity.Message
import domain.gateway.IChatGateway
import kotlinx.coroutines.flow.Flow

interface IManageMessagesUseCase {
    fun getMessages(ticketId: String): Flow<List<Message>>

    suspend fun sendMessage(message: Message)
}

class ManageMessagesUseCase(private val chatGateway: IChatGateway) : IManageMessagesUseCase {
    override fun getMessages(ticketId: String): Flow<List<Message>> {
        return chatGateway.getMessages(ticketId)
    }

    override suspend fun sendMessage(message: Message) {
        chatGateway.sendMessage(message)
    }
}