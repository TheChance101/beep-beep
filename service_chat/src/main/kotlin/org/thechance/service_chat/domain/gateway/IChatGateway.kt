package org.thechance.service_chat.domain.gateway

import org.thechance.service_chat.domain.entity.Ticket

interface IChatGateway {
    suspend fun createConversation(ticket: Ticket) : Ticket
}