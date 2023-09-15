package presentation.map

import presentation.base.ErrorState

data class MapScreenUiState(
    val isLoading: Boolean = true,
    val error: ErrorState? = null,
    val currentLocation: String = "",
    val name: String = "Kamel Mohamed",
    val isNewOrderFound: Boolean = false,
    val orderInfoUiState: OrderInfoUiState = OrderInfoUiState(),
)

data class OrderInfoUiState(
    val name: String = "",
    val pickUpAddress: String = "",
    val dropOffAddress: String = "",
    val lat: String = "",
    val lng: String = "",
)