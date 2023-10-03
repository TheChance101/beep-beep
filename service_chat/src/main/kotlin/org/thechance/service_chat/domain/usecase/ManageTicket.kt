package org.thechance.service_chat.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.koin.core.annotation.Single
import org.thechance.service_chat.domain.entity.Ticket
import org.thechance.service_chat.domain.gateway.IChatGateway
import org.thechance.service_chat.domain.utils.currentDateTime
import org.thechance.service_chat.domain.utils.toMillis
import org.thechance.service_chat.endpoints.models.SupportAgent
import java.util.concurrent.ConcurrentHashMap

interface IManageTicket {

    val supports: ConcurrentHashMap<String, SupportAgent>
    suspend fun createTicket(ticket: Ticket): Ticket
    suspend fun notifySupportAgentOfNewTickets(supportId: String): Flow<Ticket>
    suspend fun assignTicketToSupportAgent(ticket: Ticket): Ticket
}

@Single
class ManageTicket(
    private val chatGateway: IChatGateway
) : IManageTicket {


    override val supports: ConcurrentHashMap<String, SupportAgent> = ConcurrentHashMap()


    override suspend fun createTicket(ticket: Ticket): Ticket {
        val ticketId = ticket.generateTicketId()
        val currentTime = currentDateTime().toMillis()
        val newTicket = chatGateway.createTicket(ticket.copy(id = ticketId, time = currentTime))
        return assignTicketToSupportAgent(newTicket)
    }

    override suspend fun assignTicketToSupportAgent(ticket: Ticket): Ticket {
        val randomSupport = supports.keys.toList().random()
        val supportAgent = supports[randomSupport]
        val updatedTicket = chatGateway.updateTicket(ticketId = ticket.id, supportId = randomSupport)
        supportAgent?.tickets?.add(ticket)
        return updatedTicket
    }

    override suspend fun notifySupportAgentOfNewTickets(supportId: String): Flow<Ticket> {
        return flow {
            val ticket = supports[supportId]?.tickets?.poll()
            emit(ticket?.copy(supportId = supportId))
        }.flowOn(Dispatchers.IO).filterNotNull()
    }


}