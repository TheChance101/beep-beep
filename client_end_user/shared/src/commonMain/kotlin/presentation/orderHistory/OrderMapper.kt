package presentation.orderHistory

import domain.entity.Location
import domain.entity.Order
import domain.entity.Trip
import util.convertLongToFormattedDate

fun Order.toOrderHistoryUiState() = OrderHistoryUiState(
    meals = meals.joinToString(", ") { "${it.quality} ${it.name}" },
    restaurantName = restaurantName,
    restaurantImageUrl = restaurantImageUrl,
    totalPrice = price.value,
    currency = price.currency,
    createdAt = createdAt.convertLongToFormattedDate()
)

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
