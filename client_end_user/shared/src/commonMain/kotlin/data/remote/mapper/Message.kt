package data.remote.mapper

import data.remote.model.MessageDto
import domain.entity.Message
import kotlin.random.Random


fun MessageDto.toEntity(): Message {
    return Message(
        id = id ?: "",
        ticketId = ticketId ?: "",
        message = message ?: "",
        avatarUrl = senderAvatar ?: "",
        isMe = senderId == "1",
        senderId = senderId ?: ""
    )
}

fun List<MessageDto>.toEntity(): List<Message> {
    return map { it.toEntity() }
}

fun Message.toDto(): MessageDto {
    return MessageDto(
        id = Random.nextInt(0, 1000000).toString(),
        ticketId = ticketId,
        message = message,
        senderId = senderId,
    )
}