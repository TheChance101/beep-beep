package presentation.orderFoodTracking

data class OrderFoodTrackingUiState(
    val order: OrderUiState = OrderUiState(),
    val currentOrderStatus: FoodOrderStatus = FoodOrderStatus.ORDER_PLACED,
) {
    data class OrderUiState(
        val estimatedTime: String = "32:00",
        val isOrderPlaced: Boolean = false,
        val isOrderInCooking: Boolean = false,
        val isOrderInTheRoute: Boolean = false,
        val isOrderArrived: Boolean = false,
    )

    enum class FoodOrderStatus {
        ORDER_PLACED,
        ORDER_IN_COOKING,
        ORDER_IN_THE_ROUTE,
        ORDER_ARRIVED;
    }
}

