package presentation.map

import domain.entity.Location
import domain.entity.Order
import presentation.base.ErrorState

data class MapScreenUiState(
    val isLoading: Boolean = false,
    val isButtonEnabled: Boolean = true,
    val username: String = "",
    val orderState: OrderState = OrderState.LOADING,
    val errorState: ErrorState? = null,
    val orderDistance: Int = 0,
    val orderDuration: Int = 0,
    val tripId: String = "",
    val orderUiState: OrderUiState = OrderUiState(),
    val deliveryLocation: LocationUiState = LocationUiState(),
)

data class OrderUiState(
    val orderId: String = "",
    val restaurantLocation: LocationUiState = LocationUiState(),
    val destinationLocation: LocationUiState = LocationUiState(),
    val restaurantName: String = "",
    val restaurantAddress: String = "",
    val restaurantImageUrl: String = "",
    val destinationAddress: String = "",
)

data class LocationUiState(
    val lat: Double = 0.0,
    val lng: Double = 0.0,
)

fun Location.toUiState() = LocationUiState(
    lat = latitude,
    lng = longitude,
)

fun Order.toUiState() = OrderUiState(
    orderId = id,
    restaurantLocation = startPoint.toUiState(),
    destinationLocation = destination.toUiState(),
    restaurantName = restaurantName,
    restaurantAddress = startPointAddress,
    restaurantImageUrl = restaurantImage,
    destinationAddress = destinationAddress,
)

fun LocationUiState.toEntity(): Location {
    return Location(latitude = lat, longitude = lng)
}
