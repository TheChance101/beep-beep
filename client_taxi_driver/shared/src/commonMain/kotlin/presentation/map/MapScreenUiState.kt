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
    val pickUpAddress: LocationInfoUiState = LocationInfoUiState(),
    val dropOffAddress: LocationInfoUiState = LocationInfoUiState(),
    val isArrived: Boolean = false,
)

data class LocationInfoUiState(
    val lat: String = "",
    val long: String = "",
    val name: String = "",
)