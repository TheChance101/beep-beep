package org.thechance.service_notification.data.mappers

import org.thechance.service_notification.data.collection.NotificationCollection
import org.thechance.service_notification.domain.model.Notification
import org.thechance.service_notification.endpoints.model.NotificationDto

fun NotificationCollection.toEntity(): Notification {
    return Notification(
        title = title,
        body = body,
        date = date
    )
}

fun Notification.toCollection(): NotificationCollection {
    return NotificationCollection(
        title = title,
        body = body,
        date = date
    )
}
fun NotificationDto.toEntity(): Notification {
    return Notification(
        title = title,
        body = body,
        date = date
    )
}

fun Notification.toDto(): NotificationDto {
    return NotificationDto(
        title = title,
        body = body,
        date = date
    )
}

fun List<Notification>.toCollection(): List<NotificationCollection> {
    return this.map { it.toCollection() }
}

fun List<NotificationCollection>.toNotificationEntity(): List<Notification> {
    return this.map { it.toEntity() }
}

fun List<NotificationDto>.toEntity(): List<Notification> {
    return this.map { it.toEntity() }
}

fun List<Notification>.toDto(): List<NotificationDto> {
    return this.map { it.toDto() }
}
