package presentation.orderFoodTracking

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.core.parameter.parametersOf
import presentation.base.BaseScreen
import presentation.composable.BackButton
import presentation.composable.HorizontalDivider
import presentation.orderFoodTracking.composables.MapView
import resources.Resources
import util.getNavigationBarPadding
import util.getStatusBarPadding

class OrderFoodTrackingScreen(private val orderId: String, private val tripId: String) : BaseScreen<
        OrderFoodTrackingScreenModel,
        OrderFoodTrackingUiState,
        OrderFoodTrackingUiEffect,
        OrderFoodTrackingInteractionListener>() {

    @Composable
    override fun Content() {
        initScreen(getScreenModel(parameters = { parametersOf(orderId, tripId) }))
    }

    override fun onEffect(effect: OrderFoodTrackingUiEffect, navigator: Navigator) {
        when (effect) {
            OrderFoodTrackingUiEffect.NavigateBack -> navigator.pop()
        }
    }

    @Composable
    override fun onRender(
        state: OrderFoodTrackingUiState,
        listener: OrderFoodTrackingInteractionListener,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Theme.colors.background)
                .padding(getStatusBarPadding())
        ) {
            MapView(
                modifier = Modifier.fillMaxSize(),
                currentLocation = state.userLocation,
                deliveryLocation = state.deliveryLocation,
                orderState = state.order.currentOrderStatus
            )
            Column(
                modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween
            ) {
                BackButton(
                    modifier = Modifier.padding(0.dp),
                    onClick = { listener.onBackButtonClicked() })
                OrderTrackerCard(
                    currentStatusDescription = when (state.order.currentOrderStatus) {
                        OrderFoodTrackingUiState.FoodOrderStatus.ORDER_PLACED -> Resources.strings.orderPlaced
                        OrderFoodTrackingUiState.FoodOrderStatus.ORDER_ARRIVED -> Resources.strings.orderArrived
                        OrderFoodTrackingUiState.FoodOrderStatus.ORDER_IN_COOKING -> Resources.strings.orderInCooking
                        OrderFoodTrackingUiState.FoodOrderStatus.ORDER_IN_THE_ROUTE -> Resources.strings.orderInTheRoute
                    },
                    state.order,
                    modifier = Modifier.padding(getNavigationBarPadding()).padding(16.dp)
                )
            }
        }
    }


    @OptIn(ExperimentalResourceApi::class)
    @Composable
    private fun OrderTrackerCard(
        currentStatusDescription: String,
        orderStatus: OrderFoodTrackingUiState.OrderUiState,
        modifier: Modifier = Modifier,
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(Theme.radius.medium))
                .border(1.dp, Theme.colors.divider, shape = RoundedCornerShape(Theme.radius.medium))
                .background(Theme.colors.surface)
                .padding(vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = currentStatusDescription,
                    style = Theme.typography.titleLarge,
                    color = Theme.colors.contentPrimary
                )
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = Resources.strings.orderEstimatedTime,
                        style = Theme.typography.caption,
                        color = Theme.colors.contentTertiary
                    )
                    EstimatedTimeWithIcon(
                        modifier = Modifier.align(Alignment.End),
                        estimatedTime = orderStatus.estimatedTime
                    )
                }
            }
            HorizontalDivider(modifier = Modifier.fillMaxWidth())
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                OrderStatus(
                    hasFinished = orderStatus.isOrderPlaced,
                    statusIcon = painterResource(Resources.images.icTime),
                    contentDescription = "icon estimated time"
                )

                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    thickness = 1.5.dp,
                    orderHasFinished = orderStatus.isOrderInCooking
                )
                OrderStatus(
                    hasFinished = orderStatus.isOrderInCooking,
                    statusIcon = painterResource(Resources.images.icInCooking),
                    contentDescription = "icon in cooking"
                )

                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    thickness = 1.5.dp,
                    orderHasFinished = orderStatus.isOrderInTheRoute
                )

                OrderStatus(
                    hasFinished = orderStatus.isOrderInTheRoute,
                    statusIcon = painterResource(Resources.images.icScooter),
                    contentDescription = "icon scooter"
                )

                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    thickness = 1.5.dp,
                    orderHasFinished = orderStatus.isOrderArrived
                )
                OrderStatus(
                    hasFinished = orderStatus.isOrderArrived,
                    statusIcon = painterResource(Resources.images.icHome),
                    contentDescription = "icon arrived home"
                )

            }
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    private fun EstimatedTimeWithIcon(modifier: Modifier = Modifier, estimatedTime: String) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                painter = painterResource(Resources.images.icTime),
                contentDescription = "time icon",
                tint = Theme.colors.contentPrimary,
            )
            Text(
                text = estimatedTime,
                style = Theme.typography.body,
                color = Theme.colors.contentPrimary,
            )
        }
    }

    @Composable
    private fun OrderStatus(
        hasFinished: Boolean,
        statusIcon: Painter,
        contentDescription: String,
        modifier: Modifier = Modifier,
    ) {
        val borderColor by animateColorAsState(
            if (hasFinished) Theme.colors.primary
            else Theme.colors.divider
        )

        val iconColor by animateColorAsState(
            if (hasFinished) Theme.colors.primary
            else Theme.colors.contentTertiary
        )

        Box(
            modifier = modifier
                .size(48.dp)
                .clip(RoundedCornerShape(Theme.radius.medium))
                .border(1.dp, borderColor, shape = RoundedCornerShape(Theme.radius.medium))
                .background(Theme.colors.background),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = statusIcon,
                contentDescription = contentDescription,
                tint = iconColor,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
