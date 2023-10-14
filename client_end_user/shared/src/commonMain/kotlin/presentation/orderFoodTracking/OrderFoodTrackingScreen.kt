package presentation.orderFoodTracking

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import presentation.base.BaseScreen

class OrderFoodTrackingScreen() : BaseScreen<
        OrderFoodTrackingScreenModel,
        OrderFoodTrackingUiState,
        OrderFoodTrackingUiEffect,
        OrderFoodTrackingInteractionListener>() {

    @Composable
    override fun Content() {
        TODO("Not yet implemented")
    }

    override fun onEffect(effect: OrderFoodTrackingUiEffect, navigator: Navigator) {
        TODO("Not yet implemented")
    }

    @Composable
    override fun onRender(
        state: OrderFoodTrackingUiState,
        listener: OrderFoodTrackingInteractionListener
    ) {
        TODO("Not yet implemented")
    }
}