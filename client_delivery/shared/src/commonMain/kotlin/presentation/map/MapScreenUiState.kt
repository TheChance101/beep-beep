package presentation.map

import presentation.base.ErrorState

data class MapScreenUiState(
    val isLoading: Boolean = true,
    val username: String = "Youssef",
    val errorState: ErrorState? = null,
    val restaurantName: String = "",
    val restaurantLocation: String = "",
    val restaurantImageUrl: String? = null,
    val orderLocation: String = "",
    val isNewOrderFound: Boolean = false,
    val isOrderAccepted: Boolean = false
)