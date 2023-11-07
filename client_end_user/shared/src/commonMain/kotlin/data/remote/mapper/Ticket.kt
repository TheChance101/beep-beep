package data.remote.mapper

import data.remote.model.MessageDto
import data.remote.model.TicketDto
import domain.entity.Message
import domain.entity.Ticket
import domain.entity.Time

fun TicketDto.toEntity(): Ticket {
    return Ticket(
        id = id ?: "",
        openedAt = openedAt?.toTime() ?: Time(0, 0),
        messages = messages?.toEntity() ?: emptyList()
    )
}


fun MessageDto.toEntity(): Message {
    return Message(
        id = id ?: "",
        senderId = senderId ?: "",
        content = content ?: "",
        time = time?.toTime() ?: Time(0, 0)
    )
}

fun List<MessageDto>.toEntity(): List<Message> {
    return map { it.toEntity() }
}