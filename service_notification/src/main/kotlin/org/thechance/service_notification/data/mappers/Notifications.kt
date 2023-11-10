package org.thechance.service_notification.data.mappers

import org.thechance.service_notification.data.collection.NotificationHistoryCollection
import org.thechance.service_notification.domain.entity.Notification
import org.thechance.service_notification.domain.entity.NotificationHistory
import org.thechance.service_notification.endpoints.model.NotificationDto
import org.thechance.service_notification.endpoints.model.NotificationHistoryDto

fun NotificationHistoryCollection.toEntity(): NotificationHistory =
    NotificationHistory(
        id = id.toHexString(),
        topicId = topicId,
        sender = NotificationHistory.getNotificationSender(sender),
        title = title,
        body = body,
        date = date,
        userId = userId,
        topic = topic
    )


fun NotificationHistory.toCollection(): NotificationHistoryCollection {
    return NotificationHistoryCollection(
        title = title,
        topicId = topicId,
        sender = sender.code,
        body = body,
        date = date,
        userId = userId,
        topic = topic,
    )
}

fun NotificationHistoryDto.toEntity(): NotificationHistory {
    return NotificationHistory(
        title = title,
        topicId = topicId,
        sender = NotificationHistory.getNotificationSender(sender),
        body = body,
        date = date,
        userId = userId,
        topic = topic,
    )
}

fun NotificationHistory.toDto(): NotificationHistoryDto =
    NotificationHistoryDto(
        id = id,
        title = title,
        topicId = topicId,
        sender = sender.code,
        body = body,
        date = date,
        userId = userId,
        topic = topic,
    )

fun NotificationDto.toEntity(): Notification =
    Notification(
        userId = userId,
        topicId = topicId,
        sender = NotificationHistory.getNotificationSender(sender),
        title = title,
        body = body,
        topic = topic
    )

fun Notification.toNotificationHistory(): NotificationHistory =
    NotificationHistory(
        userId = userId,
        topicId = topicId,
        sender = sender,
        date = System.currentTimeMillis(),
        title = title,
        body = body,
        topic = topic
    )

fun List<NotificationHistory>.toCollection(): List<NotificationHistoryCollection> {
    return this.map { it.toCollection() }
}

fun List<NotificationHistoryCollection>.toNotificationEntity(): List<NotificationHistory> {
    return this.map { it.toEntity() }
}

fun List<NotificationHistoryDto>.toEntity(): List<NotificationHistory> {
    return this.map { it.toEntity() }
}

fun List<NotificationHistory>.toDto(): List<NotificationHistoryDto> {
    return this.map { it.toDto() }
}
