package org.thechance.service_chat.domain.usecase

import org.koin.core.annotation.Single
import org.thechance.service_chat.domain.entity.Message
import org.thechance.service_chat.domain.gateway.IChatGateway
import org.thechance.service_chat.domain.utils.currentDateTime
import org.thechance.service_chat.domain.utils.toMillis

interface IManageChat {
    suspend fun saveMessage(ticketId: String, message: Message)

}

@Single
class ManageChat(
    private val chatGateway: IChatGateway
) : IManageChat {
    override suspend fun saveMessage(ticketId: String, message: Message) {
        chatGateway.saveMessage(ticketId, message.copy(time = currentDateTime().toMillis()))
    }

}