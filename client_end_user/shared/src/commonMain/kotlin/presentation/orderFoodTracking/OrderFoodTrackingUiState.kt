package presentation.orderFoodTracking

import domain.utils.OrderingFoodStatus

data class OrderFoodTrackingUiState(
    val orderStatus: OrderStatus = OrderStatus(),
    val currentOrderStatus: OrderingFoodStatus = OrderingFoodStatus.OrderPlaced()
)

data class OrderStatus(
    val estimatedTime: String = "32:00",
    val isOrderPlaced: Boolean = true,
    val isOrderInCooking: Boolean = true,
    val isOrderInTheRoute: Boolean = false,
    val isOrderArrived: Boolean = false
)