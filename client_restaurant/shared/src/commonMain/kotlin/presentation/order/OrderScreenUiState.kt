package presentation.order

import domain.entity.Order
import domain.entity.OrderMeal
import domain.entity.OrderState

data class OrderScreenUiState(
    val inCookingOrders: List<OrderUiState> = emptyList(),
    val pendingOrders: List<OrderUiState> = emptyList(),
    val totalOrders: Int = 0,
)

data class OrderUiState(
    val id: String = "",
    val orderMealUiStates: List<OrderMealUiState> = emptyList(),
    val totalPrice: Double = 0.0,
    val orderState: OrderState = OrderState.PENDING,
    val createdAt:String = "",
)

data class OrderMealUiState(
    val mealImageUrl: String = "",
    val mealName: String = "",
    val quantity: Int = 0,
)

fun OrderMeal.toOrderMealUiState(): OrderMealUiState {
    return OrderMealUiState(
        mealName = mealName,
        mealImageUrl = mealImageUrl,
        quantity = quantity
    )
}

fun Order.toOrderUiState(): OrderUiState {
    return OrderUiState(
        id = id,
        orderMealUiStates = meals.map { it.toOrderMealUiState() },
        totalPrice = totalPrice,
        orderState = orderState,
    )
}

