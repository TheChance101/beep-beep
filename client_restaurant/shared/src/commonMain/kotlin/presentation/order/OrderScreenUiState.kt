package presentation.order

import domain.entity.Location
import domain.entity.Order
import domain.entity.OrderStatus
import domain.entity.AddressInfo

data class OrderScreenUiState(
    val isLoading: Boolean = true,
    val startPoint: LocationUiSate = LocationUiSate(),
    val startPointAddress: String = "",
    val destinationAddress: String = "",
    val destination: LocationUiSate = LocationUiSate(),
    val noInternetConnection: Boolean = false,
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
    val currency: String = "$",
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

fun Order.toOrderUiState() = OrderUiState(
    orderId = id,
    userId = userId,
    restaurantId = restaurantId,
    orderMealUiStates = meals.map { it.toOrderMealUiState() },
    totalPrice = totalPrice,
    currency = currency,
    orderState = orderState,
)

data class LocationUiSate(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
)

fun Location.toUiState() = LocationUiSate(latitude = latitude, longitude = longitude)
fun LocationUiSate.toEntity() = Location(latitude = latitude, longitude = longitude)


fun AddressInfo.toUiState(): AddressInfoUiState {
    return AddressInfoUiState(
        location = location.toUiState(),
        address = address
    )
}


data class AddressInfoUiState(
    val location: LocationUiSate = LocationUiSate(),
    val address: String = "",
)
