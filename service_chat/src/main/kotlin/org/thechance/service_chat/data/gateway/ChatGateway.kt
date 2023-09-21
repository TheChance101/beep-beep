package org.thechance.service_chat.data.gateway

import org.koin.core.annotation.Single
import org.thechance.service_chat.data.DataBaseContainer
import org.thechance.service_chat.data.collection.TicketCollection
import org.thechance.service_chat.data.collection.MessageCollection
import org.thechance.service_chat.domain.entity.Ticket
import org.thechance.service_chat.domain.entity.Message
import org.thechance.service_chat.domain.gateway.IChatGateway

@Single
class ChatGateway(private val container: DataBaseContainer) : IChatGateway {

    override suspend fun createConversation(ticket: Ticket): Ticket {
        val chatCollection = ticket.toCollection()
        container.ticketCollection.insertOne(chatCollection)
        return chatCollection.toEntity()
    }

}

fun Ticket.toCollection(): TicketCollection {
    return TicketCollection(
        ticketId = id,
        userId = userId,
        supportId = supportId,
        time = time
    )
}

fun TicketCollection.toEntity(): Ticket {
    return Ticket(
        id = ticketId,
        userId = userId,
        supportId = supportId,
        time = time,
        messages = messages.toEntity()
    )
}

fun MessageCollection.toEntity(): Message {
    return Message(
        id.toHexString(), senderId, content, time
    )
}

fun List<MessageCollection>.toEntity() = map { it.toEntity() }