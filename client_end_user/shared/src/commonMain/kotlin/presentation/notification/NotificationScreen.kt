package presentation.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.theme.Theme
import presentation.base.BaseScreen
import presentation.notification.combosable.NotificationCard

class NotificationScreen :
    BaseScreen<NotificationScreenModel, NotificationsUiState, NotificationUiEffect, NotificationInteractionListener>() {

    @Composable
    override fun Content() {
        initScreen(getScreenModel())
    }

    @Composable
    override fun onRender(state: NotificationsUiState, listener: NotificationInteractionListener) {
        LazyColumn(
            modifier = Modifier.fillMaxSize().background(Theme.colors.background),
            contentPadding = PaddingValues(vertical = 24.dp)
        ) {
            itemsIndexed(state.todayNotifications) { index, item ->
                if (index == 0) {
                    NotificationCard(
                        title = item.title,
                        showDate = true,
                        date = "Today",
                        time = item.time,
                        content = item.body,
                        isClickable = true,
                        clickableText = "Try again"
                    )
                } else {
                    NotificationCard(
                        title = item.title,
                        time = item.time,
                        content = item.body,
                    )
                }
            }

            itemsIndexed(state.thisWeekNotifications) { index, item ->
                if (index == 0) {
                    NotificationCard(
                        title = item.title,
                        showDate = true,
                        date = "This Week",
                        time = item.time,
                        content = item.body,
                        modifier = Modifier.fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp)
                    )
                } else {
                    NotificationCard(
                        title = item.title,
                        time = item.time,
                        content = item.body,
                    )
                }
            }
        }
    }

    override fun onEffect(effect: NotificationUiEffect, navigator: Navigator) {
        TODO("Not yet implemented")
    }

}