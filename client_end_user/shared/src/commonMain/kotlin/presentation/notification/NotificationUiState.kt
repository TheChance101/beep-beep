package presentation.notification

import androidx.paging.PagingData
import androidx.paging.map
import domain.entity.NotificationHistory
import domain.entity.Time
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map

data class NotificationsUiState(
    val notifications: Flow<PagingData<NotificationUiState>> = emptyFlow(),
    val isLoggedIn: Boolean = false,
)

data class NotificationUiState(
    val title: String = "",
    val body: String = "",
    val topicId: String = "",
    val sender: Int = NotificationHistory.NotificationSender.UNDEFINED.code,
    val isClickable: Boolean = false,
    val clickableText: String = "",
    val time: Time = Time(0, 0),
)

fun NotificationHistory.toUiState(): NotificationUiState {
    return NotificationUiState(
        title = title,
        body = body,
        time = time,
        topicId = topicId,
        sender = sender.code
    )
}

fun Flow<PagingData<NotificationHistory>>.toUiState(): Flow<PagingData<NotificationUiState>> {
    return this.map { pagingData -> pagingData.map { it.toUiState() } }
}