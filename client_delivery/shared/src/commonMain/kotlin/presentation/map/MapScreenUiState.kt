package presentation.map

import domain.entity.Location
import domain.entity.Order
import presentation.base.ErrorState

data class MapScreenUiState(
    val username: String = "",
    val orderState: OrderState = OrderState.LOADING,
    val errorState: ErrorState? = null,
    val orderDistance: String = "",
    val orderDuration: String = "",
    val orderUiState: OrderUiState = OrderUiState(),
    val deliveryLocation: LocationUiState = LocationUiState(),
)

data class OrderUiState(
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
    val addressName: String = "",
)
fun Location.toUiState() = LocationUiState(
    lat = latitude,
    lng = longitude,
    addressName = "",
)
fun Order.toUiState() = OrderUiState(
    restaurantLocation = startPoint.toUiState(),
    destinationLocation = destination.toUiState(),
    restaurantName = restaurantName,
    restaurantAddress = startPointAddress,
    restaurantImageUrl = restaurantImage,
    destinationAddress = destinationAddress,
)
