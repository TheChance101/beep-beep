package presentation.order

import domain.entity.Location
import domain.entity.Order
import domain.entity.OrderStatus
import domain.entity.Trip

data class OrderScreenUiState(
    val isLoading: Boolean = true,
    val noInternetConnection:Boolean = false,
    val inCookingOrders: List<OrderUiState> = emptyList(),
    val pendingOrders: List<OrderUiState> = emptyList(),
    val totalOrders: Int = 0,
)

data class OrderUiState(
    val orderId: String = "",
    val userId: String = "",
    val restaurantId: String = "",
    val orderMealUiStates: List<OrderMealUiState> = emptyList(),
    val totalPrice: Double = 0.0,
    val orderState: OrderStatus = OrderStatus.PENDING,
    val createdAt: String = "",
)

data class OrderMealUiState(
    val mealImageUrl: String = "",
    val mealName: String = "",
    val quantity: Int = 0,
)

fun Order.Meal.toOrderMealUiState(): OrderMealUiState {
    return OrderMealUiState(
        mealName = mealName,
        mealImageUrl = mealImageUrl,
        quantity = quantity
    )
}

fun Order.toOrderUiState(): OrderUiState {
    return OrderUiState(
        orderId = id,
        userId = userId,
        restaurantId = restaurantId,
        orderMealUiStates = meals.map { it.toOrderMealUiState() },
        totalPrice = totalPrice,
        orderState = orderState,
    )
}

