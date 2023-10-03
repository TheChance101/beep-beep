package org.thechance.service_chat.domain.gateway

import org.thechance.service_chat.domain.entity.Ticket

interface IChatGateway {
    suspend fun createTicket(ticket: Ticket): Ticket
    suspend fun updateTicket(ticketId: String, supportId: String): Ticket
}