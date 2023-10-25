package presentation.orderHistory

import domain.entity.Location
import domain.entity.FoodOrder
import domain.entity.Trip

fun FoodOrder.toOrderHistoryUiState() = OrderHistoryUiState(
    meals = meals.joinToString(", ") { "${it.quality} ${it.name}" },
    restaurantName = restaurantName,
    restaurantImageUrl = restaurantImageUrl,
    totalPrice = totalPrice,
    currency = currency,
    createdAt = createdAt
)

fun Trip.toTripHistoryUiState(): TripHistoryUiState {
    return TripHistoryUiState(
        taxiPlateNumber = taxiPlateNumber ?: "",
        driverName = taxiDriverName ?: "",
        startPoint = startPoint.toLocationUiState(),
        destination = destination.toLocationUiState(),
        price = price,
        endDate = ""
    )
}

fun Location.toLocationUiState(): TripHistoryUiState.LocationUiState {
    return TripHistoryUiState.LocationUiState(latitude = latitude, longitude = longitude)
}
