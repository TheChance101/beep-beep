package presentation.orderHistory

import domain.entity.Location
import domain.entity.Order
import domain.entity.Trip

data class OrderScreenUiState(
    val selectedType: OrderSelectType = OrderSelectType.MEALS,
    val ordersHistory: List<OrderHistoryUiState> = emptyList(),
    val tripsHistory: List<TripHistoryUiState> = emptyList()
) {
    enum class OrderSelectType {
        MEALS,
        TRIPS
    }
}

data class OrderHistoryUiState(
    val meals: List<MealUiState> = emptyList(),
    val restaurantName: String = "",
    val restaurantImageUrl: String = "",
    val totalPrice: Double = 0.0,
    val createdAt: Long = 0L,
) {
    data class MealUiState(
        val mealName: String = "",
        val quantity: Int = 0
    )
}

data class TripHistoryUiState(
    val taxiPlateNumber: String = "",
    val driverName: String = "",
    val startPoint: LocationUiState = LocationUiState(),
    val destination: LocationUiState = LocationUiState(),
    val price: Double = 0.0,
    val endDate: String = "",
) {
    data class LocationUiState(val latitude: Double = 0.0, val longitude: Double = 0.0)
}

fun Order.toOrderHistoryUiState(): OrderHistoryUiState {
    return OrderHistoryUiState(
        meals = meals.toMealsUiState(),
        restaurantName = restaurantName,
        restaurantImageUrl = restaurantImageUrl,
        totalPrice = totalPrice,
        createdAt = createdAt
    )
}

fun Order.Meal.toMealUiState(): OrderHistoryUiState.MealUiState {
    return OrderHistoryUiState.MealUiState(mealName = mealName, quantity = quantity)
}

fun List<Order.Meal>.toMealsUiState() = map { it.toMealUiState() }

fun Trip.toTripHistoryUiState(): TripHistoryUiState {
    return TripHistoryUiState(
        taxiPlateNumber = taxiPlateNumber,
        driverName = driverName,
        startPoint = startPoint.toLocationUiState(),
        destination = destination.toLocationUiState(),
        price = price,
        endDate = endDate
    )
}

fun Location.toLocationUiState(): TripHistoryUiState.LocationUiState {
    return TripHistoryUiState.LocationUiState(latitude = latitude, longitude = longitude)
}
