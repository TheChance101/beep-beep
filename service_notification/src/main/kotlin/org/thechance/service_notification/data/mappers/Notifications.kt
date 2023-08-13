package org.thechance.service_notification.data.mappers

import org.thechance.service_notification.data.collection.NotificationHistoryCollection
import org.thechance.service_notification.domain.entity.Notification
import org.thechance.service_notification.endpoints.model.NotificationHistoryDto

fun NotificationHistoryCollection.toEntity(): Notification =
    Notification(
        id = id.toHexString(),
        title = title,
        body = body,
        date = date,
        userId = userId,
        topic = topic
    )


fun Notification.toCollection(): NotificationHistoryCollection {
    return NotificationHistoryCollection(
        title = title,
        body = body,
        date = date,
        userId = userId,
        topic = topic,
    )
}

fun NotificationHistoryDto.toEntity(): Notification {
    return Notification(
        title = title,
        body = body,
        date = date,
        userId = userId,
        topic = topic,
    )
}

fun Notification.toDto(): NotificationHistoryDto =
    NotificationHistoryDto(
        title = title,
        body = body,
        date = date,
        userId = userId,
        topic = topic,
    )


fun List<Notification>.toCollection(): List<NotificationHistoryCollection> {
    return this.map { it.toCollection() }
}

fun List<NotificationHistoryCollection>.toNotificationEntity(): List<Notification> {
    return this.map { it.toEntity() }
}

fun List<NotificationHistoryDto>.toEntity(): List<Notification> {
    return this.map { it.toEntity() }
}

fun List<Notification>.toDto(): List<NotificationHistoryDto> {
    return this.map { it.toDto() }
}
