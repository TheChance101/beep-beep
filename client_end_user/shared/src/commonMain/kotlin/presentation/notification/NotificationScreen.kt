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
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.auth.login.LoginScreen
import presentation.base.BaseScreen
import presentation.composable.ContentVisibility
import presentation.composable.LoginRequiredPlaceholder
import presentation.notification.combosable.NotificationCard
import resources.Resources
import util.getStatusBarPadding
import util.root

class NotificationScreen : BaseScreen<
        NotificationScreenModel,
        NotificationsUiState,
        NotificationUiEffect,
        NotificationInteractionListener
        >() {

    @Composable
    override fun Content() {
        initScreen(getScreenModel())
    }

    override fun onEffect(effect: NotificationUiEffect, navigator: Navigator) {
        when (effect) {
            is NotificationUiEffect.MakeOrderAgain -> println("order again")
            is NotificationUiEffect.NavigateToTraceOrderScreen -> println("navigate to trace order screen")
            NotificationUiEffect.NavigateToLoginScreen -> navigator.root?.push(LoginScreen())
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun onRender(state: NotificationsUiState, listener: NotificationInteractionListener) {

        LoginRequiredPlaceholder(
            placeHolder = painterResource(Resources.images.requireLoginToShowNotificationPlaceholder),
            message = Resources.strings.notificationLoginMessage,
            onClickLogin = listener::onClickLogin
        )

        ContentVisibility(state.isLoggedIn) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Theme.colors.background)
                    .padding(getStatusBarPadding()),
                contentPadding = PaddingValues(vertical = 24.dp)
            ) {
                itemsIndexed(state.todayNotifications) { index, item ->
                    val showDate = index == 0
                    NotificationCard(
                        title = item.title,
                        showDate = showDate,
                        date = Resources.strings.today,
                        time = item.time,
                        content = item.body,
                        isClickable = true,
                        clickableText = Resources.strings.tryAgain
                    )
                }

                itemsIndexed(state.thisWeekNotifications) { index, item ->
                    if (index == 0) {
                        NotificationCard(
                            title = item.title,
                            showDate = true,
                            date = Resources.strings.thisWeek,
                            time = item.time,
                            content = item.body,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp),
                            isClickable = true,
                            clickableText = Resources.strings.trackYourOrder
                        )
                    } else {
                        NotificationCard(title = item.title, time = item.time, content = item.body)
                    }
                }
            }
        }
    }
}