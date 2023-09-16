package presentation.map

import presentation.base.ErrorState

data class MapScreenUiState(
    val isLoading: Boolean = true,
    val error: ErrorState? = null,
    val userName: String = "Kamel Mohamed!",
    val isNewOrderFound: Boolean = false,
    val isAcceptedOrder: Boolean = false,
    val orderInfoUiState: OrderInfoUiState = OrderInfoUiState(),
)

data class OrderInfoUiState(
    val passengerName: String = "",
    val pickUpAddress: String = "",
    val dropOffAddress: String = "",
    val isArrived: Boolean = false,
)