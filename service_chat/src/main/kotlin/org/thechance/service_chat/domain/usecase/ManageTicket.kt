package org.thechance.service_chat.domain.usecase

import org.koin.core.annotation.Single
import org.thechance.service_chat.domain.entity.Ticket
import org.thechance.service_chat.domain.gateway.IChatGateway
import org.thechance.service_chat.domain.utils.currentDateTime
import org.thechance.service_chat.domain.utils.toMillis

interface IManageTicket {
    suspend fun createConversation(ticket: Ticket) : Ticket
}

@Single
class ManageTicket(
    private val chatGateway: IChatGateway
) : IManageTicket {
    override suspend fun createConversation(ticket: Ticket): Ticket {
        val ticketId = ticket.generateTicketId()
        val currentTime = currentDateTime().toMillis()
        return chatGateway.createConversation(ticket.copy(id = ticketId, time = currentTime))
    }

}