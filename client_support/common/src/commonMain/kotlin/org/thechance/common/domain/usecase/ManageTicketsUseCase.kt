package org.thechance.common.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.thechance.common.domain.entity.Ticket
import org.thechance.common.domain.gateway.IChatGateway

interface IManageTicketsUseCase {
    fun getTickets(): Flow<Ticket?>

    suspend fun closeTicket(ticketId: String)
}

class ManageTicketsUseCase(private val chatGateway: IChatGateway) : IManageTicketsUseCase {
    override fun getTickets(): Flow<Ticket?> {
        return chatGateway.getTickets()
    }

    override suspend fun closeTicket(ticketId: String) {
        chatGateway.closeTicket(ticketId)
    }
}