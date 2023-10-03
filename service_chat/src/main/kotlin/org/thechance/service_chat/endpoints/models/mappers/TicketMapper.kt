package org.thechance.service_chat.endpoints.models.mappers

import org.thechance.service_chat.domain.entity.Message
import org.thechance.service_chat.domain.entity.Ticket
import org.thechance.service_chat.endpoints.models.MessageDto
import org.thechance.service_chat.endpoints.models.TicketDto

fun Ticket.toDto(): TicketDto {
    return TicketDto(
        id = id,
        userId = userId,
        supportId = supportId,
        time = time,
        messages = messages.map { it.toDto() }
    )
}

fun Message.toDto(): MessageDto {
    return MessageDto(
        id = id,
        senderId = senderId,
        content = content,
        time = time
    )
}


fun TicketDto.toEntity(): Ticket {
    return Ticket(
        id = id ?: "",
        userId = userId,
        supportId = supportId ?: "",
        time = time ?: 0,
        messages = messages?.map { it.toEntity() } ?: emptyList()
    )
}

fun MessageDto.toEntity(): Message {
    return Message(
        id = id ?: "",
        senderId = senderId ?: "",
        content = content ?: "",
        time = time ?: 0
    )
}