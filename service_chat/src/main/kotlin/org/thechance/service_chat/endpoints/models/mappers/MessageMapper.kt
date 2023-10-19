package org.thechance.service_chat.endpoints.models.mappers

import org.thechance.service_chat.domain.entity.Message
import org.thechance.service_chat.endpoints.models.MessageDto


fun Message.toDto(): MessageDto {
    return MessageDto(
        id = id,
        senderId = senderId,
        content = content,
        time = time
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