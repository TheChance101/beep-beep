package presentation.orderFoodTracking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.theme.Theme
import domain.utils.OrderingFoodStatus
import presentation.base.BaseScreen
import presentation.composable.BackButton
import presentation.home.HomeScreen
import presentation.orderFoodTracking.composable.OrderTrackerCard
import resources.Resources
import util.getNavigationBarPadding
import util.getStatusBarPadding

class OrderFoodTrackingScreen : BaseScreen<
        OrderFoodTrackingScreenModel,
        OrderFoodTrackingUiState,
        OrderFoodTrackingUiEffect,
        OrderFoodTrackingInteractionListener>() {

    @Composable
    override fun Content() {
        initScreen(getScreenModel())
    }

    override fun onEffect(effect: OrderFoodTrackingUiEffect, navigator: Navigator) {
        when (effect) {
            OrderFoodTrackingUiEffect.NavigateBack -> navigator.replace(HomeScreen())
        }
    }

    @Composable
    override fun onRender(
        state: OrderFoodTrackingUiState,
        listener: OrderFoodTrackingInteractionListener
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Theme.colors.background)
                .padding(getStatusBarPadding())
        ) {
            Column(
                modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween
            ) {
                BackButton(
                    modifier = Modifier.padding(0.dp),
                    onClick = { listener.onBackButtonClicked() })
                OrderTrackerCard(
                    currentStatusDescription = when (state.currentOrderStatus) {
                        is OrderingFoodStatus.OrderPlaced -> Resources.strings.orderPlaced
                        is OrderingFoodStatus.OrderArrived -> Resources.strings.orderArrived
                        is OrderingFoodStatus.OrderInCooking -> Resources.strings.orderInCooking
                        is OrderingFoodStatus.OrderInTheRoute -> Resources.strings.orderInTheRoute
                    },
                    state.orderStatus,
                    modifier = Modifier.padding(getNavigationBarPadding()).padding(16.dp)
                )
            }
        }

    }
}