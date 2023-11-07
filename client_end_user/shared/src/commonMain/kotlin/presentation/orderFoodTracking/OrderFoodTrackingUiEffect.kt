package presentation.orderFoodTracking

sealed class OrderFoodTrackingUiEffect {
    data object NavigateBack : OrderFoodTrackingUiEffect()
}
