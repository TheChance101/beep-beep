package presentation.orderHistory

import domain.entity.Location
import domain.entity.MealCart
import domain.entity.Order
import domain.entity.Trip
import presentation.cart.toUiState
import presentation.resturantDetails.MealUIState

data class OrderScreenUiState(
    val selectedType: OrderSelectType = OrderSelectType.MEALS,
    val ordersHistory: List<OrderHistoryUiState> = emptyList(),
    val tripsHistory: List<TripHistoryUiState> = emptyList(),
    val isLoggedIn: Boolean = false
) {
    enum class OrderSelectType {
        MEALS,
        TRIPS
    }
}

data class OrderHistoryUiState(
    val meals: List<MealUIState> = emptyList(),
    val restaurantName: String = "",
    val restaurantImageUrl: String = "",
    val totalPrice: Double = 0.0,
    val createdAt: Long = 0L,
)

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

fun Order.toOrderHistoryUiState() = OrderHistoryUiState(
    meals = meals.toMealUIState(),
    restaurantName = restaurantName,
    restaurantImageUrl = restaurantImageUrl,
    totalPrice = price.value,
    createdAt = createdAt
)

fun MealCart.toMealUIState() = MealUIState(
    id = id,
    name = name,
    restaurantName = restaurantName,
    price = price.value,
    totalPrice = price.value,
    image = imageUrl,
    currency = price.currency,
    quantity = quality,
)

fun List<MealCart>.toMealUIState()= map{it.toMealUIState()}

fun Trip.toTripHistoryUiState(): TripHistoryUiState {
    return TripHistoryUiState(
        taxiPlateNumber = taxiPlateNumber,
        driverName = driverName,
        startPoint = startPoint.toLocationUiState(),
        destination = destination.toLocationUiState(),
        price = price.value,
        endDate = ""
    )
}

fun Location.toLocationUiState(): TripHistoryUiState.LocationUiState {
    return TripHistoryUiState.LocationUiState(latitude = latitude, longitude = longitude)
}
