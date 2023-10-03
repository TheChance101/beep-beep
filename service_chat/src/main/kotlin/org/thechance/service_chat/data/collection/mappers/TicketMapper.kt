package org.thechance.service_chat.data.collection.mappers

import org.thechance.service_chat.data.collection.MessageCollection
import org.thechance.service_chat.data.collection.TicketCollection
import org.thechance.service_chat.domain.entity.Message
import org.thechance.service_chat.domain.entity.Ticket


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
        id.toHexString().toString(), senderId, content, time
    )
}

fun List<MessageCollection>.toEntity() = map { it.toEntity() }