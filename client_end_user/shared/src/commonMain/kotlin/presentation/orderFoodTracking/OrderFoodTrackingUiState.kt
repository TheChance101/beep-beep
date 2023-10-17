package presentation.orderFoodTracking

data class OrderFoodTrackingUiState(
    val orderStatus: OrderStatus = OrderStatus(),
    val currentOrderStatus: FoodOrderStatus = FoodOrderStatus.ORDER_PLACED,
)

data class OrderStatus(
    val estimatedTime: String = "32:00",
    val isOrderPlaced: Boolean = true,
    val isOrderInCooking: Boolean = true,
    val isOrderInTheRoute: Boolean = false,
    val isOrderArrived: Boolean = false,
)

enum class FoodOrderStatus {
    ORDER_PLACED,
    ORDER_IN_COOKING,
    ORDER_IN_THE_ROUTE,
    ORDER_ARRIVED;
}