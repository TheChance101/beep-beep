package presentation.notification

import androidx.paging.PagingData
import androidx.paging.map
import domain.entity.Date
import domain.entity.NotificationHistory
import domain.entity.Time
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Month

data class NotificationsUiState(
    val notifications: Flow<PagingData<NotificationUiState>> = emptyFlow(),
    val todayNotifications: List<NotificationUiState> = emptyList(),
    val isLoggedIn: Boolean = false,
)

data class NotificationUiState(
    val title: String = "",
    val body: String = "",
    val topicId: String = "",
    val sender: Int = NotificationHistory.NotificationSender.UNDEFINED.code,
    val isClickable: Boolean = false,
    val clickableText: String = "",
    val date: Date = Date(24, Month.AUGUST, 2023),
    val time: Time = Time(0, 0),
)

fun NotificationHistory.toUiState(): NotificationUiState {
    return NotificationUiState(
        title = title,
        body = body,
        time = time,
        date = date,
        topicId = topicId,
        sender = sender.code
    )
}

fun List<NotificationHistory>.toUiState(): List<NotificationUiState> {
    return this.map { it.toUiState() }
}


fun Flow<PagingData<NotificationHistory>>.toUiState(): Flow<PagingData<NotificationUiState>> {
    return this.map { pagingData -> pagingData.map { it.toUiState() } }
}