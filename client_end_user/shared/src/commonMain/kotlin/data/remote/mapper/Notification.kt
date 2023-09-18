package data.remote.mapper

import data.remote.model.NotificationDto
import domain.entity.Notification
import domain.entity.Time

fun NotificationDto.toEntity(): Notification{
    return Notification(
        id = id ?: "",
        title = title,
        body = body,
        date = date,
        time = time?.toTime()!!,
        userId = userId ?: "",
        topic = topic ?: "",
    )
}