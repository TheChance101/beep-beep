package presentation.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.cash.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BpPagingList
import com.beepbeep.designSystem.ui.theme.Theme
import domain.entity.NotificationHistory
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.auth.login.LoginScreen
import presentation.base.BaseScreen
import presentation.composable.ContentVisibility
import presentation.composable.LoginRequiredPlaceholder
import presentation.notification.combosable.NotificationCard
import presentation.orderFoodTracking.OrderFoodTrackingScreen
import presentation.taxi.TaxiOrderScreen
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
            is NotificationUiEffect.NavigateToLoginScreen -> navigator.root?.push(LoginScreen())
            is NotificationUiEffect.NavigateToTrackFoodOrder -> navigator.root?.push(
                OrderFoodTrackingScreen(orderId = effect.orderId, tripId = "")
            )

            is NotificationUiEffect.NavigateToTrackDelivery -> navigator.root?.push(
                OrderFoodTrackingScreen(tripId = effect.tripId, orderId = "")
            )

            is NotificationUiEffect.NavigateToTaxiRide -> navigator.root?.push(TaxiOrderScreen())
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

                BpPagingList(
                    data = notifications,
                    verticalArrangement = Arrangement.spacedBy(0.dp),
                    optionalHeaderTitle = Resources.strings.today,
                    optionalTopLList = state.todayNotifications,
                    hasOptionalList = state.todayNotifications.isNotEmpty(),
                    optionalContent = { notification ->
                        NotificationCard(
                            title = notification.title,
                            content = notification.body,
                            time = notification.time,
                            isClickable =
                            NotificationHistory.getNotificationSender(notification.sender)
                                    != NotificationHistory.NotificationSender.UNDEFINED,
                            clickableText = notification.notificationClickableText,
                            onClickNotification = {
                                listener.onClickNotification(
                                    notification.topicId,
                                    notification.sender
                                )
                            },
                            cardShape = if (state.todayNotifications.indexOf(notification) == 0) {
                                RoundedCornerShape(bottomEnd = 8.dp, bottomStart = 8.dp)
                            } else {
                                RoundedCornerShape(8.dp)
                            }
                        )
                    },
                    content = { notificationUiState,modifier ->
                        notificationUiState?.let { notification ->
                            NotificationCard(
                                modifier =modifier,
                                title = notification.title,
                                content = notification.body,
                                time = notification.time,
                                date = notification.date,
                                showDate = true
                            )
                        }
                    }
                )
            }
        }
    }
}