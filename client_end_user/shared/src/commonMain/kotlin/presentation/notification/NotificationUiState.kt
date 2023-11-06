package presentation.notification

import androidx.compose.runtime.Composable
import androidx.paging.PagingData
import domain.entity.Date
import domain.entity.NotificationHistory
import domain.entity.Time
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.datetime.Month
import resources.Resources

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
) {
    val notificationClickableText: String
        @Composable
        get() {
            return when (sender) {
                NotificationHistory.NotificationSender.RESTAURANT.code -> Resources.strings.trackYourOrder
                NotificationHistory.NotificationSender.DELIVERY.code -> Resources.strings.trackYourOrder
                NotificationHistory.NotificationSender.TAXI.code -> Resources.strings.trackYourRide
                else -> ""
            }
        }
}