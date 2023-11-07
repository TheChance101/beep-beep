package domain.gateway

import domain.entity.Ticket

interface IChatGateway {
    suspend fun createTicket(userId: String) : Ticket
}