package presentation.map

import presentation.base.ErrorState

data class MapScreenUiState(
    val username: String = "Youssef",
    val currentLocation: LocationUiState = LocationUiState(),
    val destinationLocation: LocationUiState = LocationUiState(),
    val orderState: OrderState = OrderState.LOADING,
    val errorState: ErrorState? = null,
    val restaurantName: String = "Restaurant Name",
    val restaurantLocation: String = "Restaurant Location",
    val restaurantImageUrl: String? = null,
    val orderLocation: String = "Alex,Egypt",
    val orderDistance: String = "20 KM",
    val orderDuration: String = "20 min"
)

data class LocationUiState(
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val addressName: String = ""
)
