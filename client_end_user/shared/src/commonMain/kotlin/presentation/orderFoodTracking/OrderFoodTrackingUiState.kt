package presentation.orderFoodTracking

import domain.utils.OrderingFoodStatus

data class OrderFoodTrackingUiState(
    val orderStatus: OrderStatus = OrderStatus(),
    val currentOrderStatus: OrderingFoodStatus = OrderingFoodStatus.OrderPlaced()
)

data class OrderStatus(
    val isOrderPlaced: Boolean = false,
    val isOrderInCooking: Boolean = false,
    val isOrderInTheRoute: Boolean = false,
    val isOrderArrived: Boolean = false
)