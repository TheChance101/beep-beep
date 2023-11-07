package presentation.taxi

import presentation.orderFoodTracking.LocationUiState


data class TaxiOrderUiState(
    val currentLocation: LocationUiState = LocationUiState(),
    val destination: LocationUiState = LocationUiState(),

    val query: String = "",
)
