package presentation.notification

import domain.entity.NotificationHistory
import domain.entity.Time

data class NotificationsUiState(
    val todayNotifications: List<NotificationUiState> = emptyList(),
    val thisWeekNotifications: List<NotificationUiState> = emptyList(),
    val isLoggedIn: Boolean = false,
)

data class NotificationUiState(
    val title: String = "",
    val body: String = "",
    val isClickable: Boolean = false,
    val clickableText: String = "",
    val time: Time = Time(0, 0),
)

fun NotificationHistory.toUiState(): NotificationUiState {
    return NotificationUiState(
        title = title,
        body = body,
        time = time
    )
}

fun List<NotificationHistory>.toUiState(): List<NotificationUiState> {
    return this.map { it.toUiState() }
}