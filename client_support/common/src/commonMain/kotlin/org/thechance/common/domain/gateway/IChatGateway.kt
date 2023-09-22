package org.thechance.common.domain.gateway

import kotlinx.coroutines.flow.Flow
import org.thechance.common.domain.entity.Message
import org.thechance.common.domain.entity.NewMessage
import org.thechance.common.domain.entity.Ticket

interface IChatGateway {

    fun getTickets(): Flow<Ticket?>

    suspend fun closeTicket(ticketId: String)

    fun getMessages(ticketId: String): Flow<List<Message>>

    suspend fun sendMessage(message: NewMessage)
}