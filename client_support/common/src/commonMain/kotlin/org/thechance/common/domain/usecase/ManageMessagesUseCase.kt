package org.thechance.common.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.thechance.common.domain.entity.Message
import org.thechance.common.domain.entity.NewMessage
import org.thechance.common.domain.gateway.IChatGateway

interface IManageMessagesUseCase {
    fun getMessages(ticketId: String): Flow<List<Message>>

    suspend fun sendMessage(message: NewMessage)
}

class ManageMessagesUseCase(private val chatGateway: IChatGateway) : IManageMessagesUseCase {
    override fun getMessages(ticketId: String): Flow<List<Message>> {
        return chatGateway.getMessages(ticketId)
    }

    override suspend fun sendMessage(message: NewMessage) {
        chatGateway.sendMessage(message)
    }
}