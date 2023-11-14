package presentation.map

import presentation.base.ErrorState

data class MapScreenUiState(
    val isLoading: Boolean = true,
    val error: ErrorState? = null,
    val driverName: String = "",
    val currentLocation: LocationInfoUiState = LocationInfoUiState(),
    val isNewOrderFound: Boolean = false,
    val isAcceptedOrder: Boolean = false,
    val tripInfoUiState: TripInfoUiState = TripInfoUiState(),
)

data class TripInfoUiState(
    val id: String = "",
    val passengerName: String = "",
    val pickUpLocation: LocationInfoUiState? = null,
    val dropOffLocation: LocationInfoUiState? = null,
    val pickUpAddress: String = "",
    val dropOffAddress: String = "",
    val isArrived: Boolean = false,
)

data class LocationInfoUiState(
    val lat: Double = 0.0,
    val lng: Double = 0.0,
)