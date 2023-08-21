package presentation.order

import util.OrderState

data class OrderScreenUiState(
    val mealImageUrl: String = "",
    val mealName: String = "",
    val quantity: Int = 0,
    val totalPrice: Double = 0.0,
    val orderState: Int = OrderState.PENDING.statusCode,
    val totalOrders: Int = 0
)
