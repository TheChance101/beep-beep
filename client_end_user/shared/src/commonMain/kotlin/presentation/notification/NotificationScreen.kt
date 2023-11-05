package presentation.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.cash.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BpPagingList
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

        val notifications = state.notifications.collectAsLazyPagingItems()

        LoginRequiredPlaceholder(
            placeHolder = painterResource(Resources.images.requireLoginToShowNotificationPlaceholder),
            message = Resources.strings.notificationLoginMessage,
            onClickLogin = listener::onClickLogin
        )

        ContentVisibility(state.isLoggedIn) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Theme.colors.background)
                    .padding(getStatusBarPadding()),
            ) {
                BpPagingList(data = notifications) { notificationUiState ->
                    notificationUiState?.let {
//                        if (it.time.hours < 24) {
                        NotificationCard(
                            title = it.title,
                            content = it.body,
                            time = it.time,
                        )
//                        }
                    }
                }
            }
        }
    }
}