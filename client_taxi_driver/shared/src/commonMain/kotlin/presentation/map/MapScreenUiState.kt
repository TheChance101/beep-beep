package presentation.map

import presentation.base.ErrorState

data class MapScreenUiState(
    val isLoading: Boolean = true,
    val error: ErrorState? = null,
    val userName: String = "",
    val currentLocation: LocationInfoUiState = LocationInfoUiState(),
    val isNewOrderFound: Boolean = false,
    val isAcceptedOrder: Boolean = false,
    val orderInfoUiState: OrderInfoUiState = OrderInfoUiState(),
)

data class OrderInfoUiState(
    val passengerName: String = "",
    val pickUpAddress: LocationInfoUiState? = null,
    val dropOffAddress: LocationInfoUiState? = null,
    val isArrived: Boolean = false,
)

data class LocationInfoUiState(
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val addressName: String = "",
)