package data.remote.mapper

import data.remote.model.NotificationHistoryDto
import domain.entity.NotificationHistory

fun NotificationHistoryDto.toEntity(): NotificationHistory {
    return NotificationHistory(
        id = id ?: "",
        topicId = topicId ?: "",
        title = title,
        body = body,
        sender = NotificationHistory.getNotificationSender(sender),
        date = date.toDate(),
        time = date.toTime(),
        userId = userId ?: "",
        topic = topic ?: "",
    )
}