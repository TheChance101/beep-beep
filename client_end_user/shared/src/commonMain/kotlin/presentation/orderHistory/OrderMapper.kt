package presentation.orderHistory

import domain.entity.Location
import domain.entity.MealCart
import domain.entity.Order
import domain.entity.Trip
import presentation.resturantDetails.MealUIState

fun Order.toOrderHistoryUiState() = OrderHistoryUiState(
    meals = meals.toMealUIState(),
    restaurantName = restaurantName,
    restaurantImageUrl = restaurantImageUrl,
    totalPrice = price.value,
    currency = price.currency,
    createdAt = createdAt
)

fun MealCart.toMealUIState() = MealUIState(
    id = id,
    name = name,
    price = price.value,
    totalPrice = price.value,
    image = imageUrl,
    currency = price.currency,
    quantity = quality,
)

fun List<MealCart>.toMealUIState() = map { it.toMealUIState() }

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
