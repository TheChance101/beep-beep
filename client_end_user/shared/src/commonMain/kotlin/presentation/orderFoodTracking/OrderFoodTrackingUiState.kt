package presentation.orderFoodTracking

data class OrderFoodTrackingUiState(
    val order: OrderUiState = OrderUiState(),
    val currentOrderStatus: FoodOrderStatus = FoodOrderStatus.ORDER_PLACED,
    val userLocation: LocationUiState = LocationUiState(),
    val deliveryLocation: LocationUiState = LocationUiState(),
) {

    data class LocationUiState(
        val latitude: Double = 0.0,
        val longitude: Double = 0.0,
    )

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

