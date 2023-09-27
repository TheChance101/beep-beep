package org.thechance.common.data.mapper

import org.thechance.common.data.remote.model.TicketDto
import org.thechance.common.domain.entity.Ticket

fun TicketDto.toEntity(): Ticket {
    return Ticket(
        id = id ?: "",
        username = username ?: "",
        avatar = avatar ?: "",
        openedAt = openedAt ?: "",
    )
}

fun List<TicketDto>.toEntity(): List<Ticket> {
    return map { it.toEntity() }
}