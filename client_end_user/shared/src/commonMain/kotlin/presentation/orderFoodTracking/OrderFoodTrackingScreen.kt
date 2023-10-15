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
        Box(modifier = Modifier.fillMaxSize().background(color = Theme.colors.background)) {
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(top = 24.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                BackButton(onClick = { listener.onBackButtonClicked() })
                OrderTrackerCard(
                    currentStatusDescription = when (state.currentOrderStatus) {
                        is OrderingFoodStatus.OrderArrived -> "Your Feast Has Arrived."
                        is OrderingFoodStatus.OrderInCooking -> "Your meal in the oven."
                        is OrderingFoodStatus.OrderInTheRoute -> "Your Food Is En Route."
                        is OrderingFoodStatus.OrderPlaced -> "Order placed."
                    },
                    state.orderStatus,
                )
            }
        }

    }
}