package data.remote.mapper

import data.remote.model.TicketDto
import domain.entity.Ticket
import domain.entity.Time

fun TicketDto.toEntity(): Ticket {
    return Ticket(
        id = id ?: "",
        username = username ?: "",
        avatar = avatar ?: "",
        openedAt = openedAt?.toTime() ?: Time(0,0),
    )
}

fun List<TicketDto>.toEntity(): List<Ticket> {
    return map { it.toEntity() }
}