package org.thechance.common.data.mapper

import org.thechance.common.data.remote.model.MessageDto
import org.thechance.common.domain.entity.Message
import org.thechance.common.domain.entity.NewMessage
import kotlin.random.Random


fun MessageDto.toEntity(): Message {
    return Message(
        id = id ?: "",
        ticketId = ticketId ?: "",
        message = message ?: "",
        avatarUrl = senderAvatar ?: "",
        isMe = senderId == "1"
    )
}

fun List<MessageDto>.toEntity(): List<Message> {
    return map { it.toEntity() }
}

fun NewMessage.toDto(): MessageDto {
    return MessageDto(
        id = Random.nextInt(0, 1000000).toString(),
        ticketId = ticketId,
        message = message,
        senderId = senderId,
    )
}