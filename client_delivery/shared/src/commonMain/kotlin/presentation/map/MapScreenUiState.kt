package presentation.map

import presentation.base.ErrorState

data class MapScreenUiState(
    val username: String = "",
    val currentLocation: LocationUiState = LocationUiState(),
    val destinationLocation: LocationUiState = LocationUiState(),
    val orderState: OrderState = OrderState.LOADING,
    val errorState: ErrorState? = null,
    val restaurantName: String = "",
    val restaurantLocation: String = "",
    val restaurantImageUrl: String? = null,
    val orderLocation: String = "",
    val orderDistance: String = "",
    val orderDuration: String = ""
)

data class LocationUiState(
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val addressName: String = ""
)
