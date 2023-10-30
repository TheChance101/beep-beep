package presentation.orderFoodTracking

import domain.entity.Location

data class OrderFoodTrackingUiState(
    val isLoading: Boolean = false,
    val order: OrderUiState = OrderUiState(),
    val userLocation: LocationUiState = LocationUiState(),
    val deliveryLocation: LocationUiState = LocationUiState(),
) {
    data class OrderUiState(
        val currentOrderStatus: FoodOrderStatus = FoodOrderStatus.ORDER_PLACED,
    ) {
        val estimatedTime: String
            get() = when (currentOrderStatus) {
                FoodOrderStatus.ORDER_PLACED -> "32:00"
                FoodOrderStatus.ORDER_IN_COOKING -> "20:00"
                FoodOrderStatus.ORDER_IN_THE_ROUTE -> "15:00"
                FoodOrderStatus.ORDER_ARRIVED -> "00:00"
            }
        val isOrderPlaced: Boolean
            get() = currentOrderStatus >= FoodOrderStatus.ORDER_PLACED

        val isOrderInCooking: Boolean
            get() = currentOrderStatus >= FoodOrderStatus.ORDER_IN_COOKING

        val isOrderInTheRoute: Boolean
            get() = currentOrderStatus >= FoodOrderStatus.ORDER_IN_THE_ROUTE

        val isOrderArrived: Boolean
            get() = currentOrderStatus >= FoodOrderStatus.ORDER_ARRIVED
    }

    enum class FoodOrderStatus {
        ORDER_PLACED,
        ORDER_IN_COOKING,
        ORDER_IN_THE_ROUTE,
        ORDER_ARRIVED;
    }
}

data class LocationUiState(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
)

fun Location.toUiState() = LocationUiState(
    latitude = latitude,
    longitude = longitude,
)

