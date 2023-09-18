package presentation.notification

import domain.entity.Notification
import domain.entity.Time


data class NotificationsUiState(
    val todayNotifications: List<NotificationUiState> = emptyList(),
    val thisWeekNotifications: List<NotificationUiState> = emptyList()
)
data class  NotificationUiState(
    val title: String = "",
    val body: String = "",
    val isClickable: Boolean = false,
    val clickableText: String = "",
    val time: Time = Time(0,0),
)

fun Notification.toUiState(): NotificationUiState{
    return NotificationUiState(
        title = title,
        body = body,
        time = time
    )
}

fun List<Notification>.toUiState(): List<NotificationUiState>{
    return this.map { it.toUiState() }
}