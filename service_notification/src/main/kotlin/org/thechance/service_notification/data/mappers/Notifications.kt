package org.thechance.service_notification.data.mappers

import org.bson.types.ObjectId
import org.thechance.service_notification.data.collection.HistoryNotificationCollection
import org.thechance.service_notification.domain.model.Notification
import org.thechance.service_notification.endpoints.model.NotificationDto

fun HistoryNotificationCollection.toEntity(): Notification =
    Notification(title, body, date, userId.toHexString(), isRead)


fun Notification.toCollection(): HistoryNotificationCollection {
    return HistoryNotificationCollection(
        title = title, body = body, date = date, userId = ObjectId(userId)
    )
}

fun NotificationDto.toEntity(): Notification {
    return Notification(
        title = title, body = body, date = date, userId = userId, isRead = isRead
    )
}

fun Notification.toDto(): NotificationDto =
    NotificationDto(title = title, body = body, date = date, userId = userId, isRead = isRead)


fun List<Notification>.toCollection(): List<HistoryNotificationCollection> {
    return this.map { it.toCollection() }
}

fun List<HistoryNotificationCollection>.toNotificationEntity(): List<Notification> {
    return this.map { it.toEntity() }
}

fun List<NotificationDto>.toEntity(): List<Notification> {
    return this.map { it.toEntity() }
}

fun List<Notification>.toDto(): List<NotificationDto> {
    return this.map { it.toDto() }
}
