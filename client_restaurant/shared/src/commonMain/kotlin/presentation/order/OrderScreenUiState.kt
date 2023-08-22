package presentation.order

import util.OrderState

data class OrderScreenUiState(
    val orders: List<OrderUiState> = emptyList(),
    val totalOrders: Int = 0,
) {
    data class OrderUiState(
        val orderMeals: List<OrderMeals> = emptyList(),
        val totalPrice: Double = 0.0,
        val orderState: Int = OrderState.PENDING.statusCode,
    ) {
        data class OrderMeals(
            val mealImageUrl: String = "",
            val mealName: String = "",
            val quantity: Int = 0,
        )
    }
}