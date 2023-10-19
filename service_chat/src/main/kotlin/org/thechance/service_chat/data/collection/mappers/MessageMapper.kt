package org.thechance.service_chat.data.collection.mappers

import org.thechance.service_chat.data.collection.MessageCollection
import org.thechance.service_chat.domain.entity.Message


fun MessageCollection.toEntity(): Message {
    return Message(
        id.toHexString().toString(), senderId, content, time
    )
}

fun Message.toCollection(): MessageCollection {
    return MessageCollection(
        senderId = senderId,
        content = content,
        time = time
    )
}

fun List<MessageCollection>.toEntity() = map { it.toEntity() }