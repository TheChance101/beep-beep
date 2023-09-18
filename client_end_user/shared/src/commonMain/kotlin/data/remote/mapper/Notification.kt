package data.remote.mapper

import data.remote.model.NotificationDto
import domain.entity.Notification

fun NotificationDto.toEntity(): Notification {
    return Notification(
        id = id ?: "",
        title = title,
        body = body,
        date = date,
        time = date.toTime(),
        userId = userId ?: "",
        topic = topic ?: "",
    )
}