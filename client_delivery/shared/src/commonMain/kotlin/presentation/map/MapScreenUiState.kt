package presentation.map

import presentation.base.ErrorState

data class MapScreenUiState(
    val isLoading: Boolean = true,
    val username: String = "Youssef",
    val errorState: ErrorState? = null,
    val restaurantName: String = "Restaurant Name",
    val restaurantLocation: String = "Restaurant Location",
    val restaurantImageUrl: String? = null,
    val orderLocation: String = "Alex,Egypt",
    val isNewOrderFound: Boolean = false,
    val showChoiceButtons: Boolean = false,
    val showOrderDuration: Boolean = false,
    val showReceivedButton: Boolean = false,
    val orderDistance: String = "20 KM",
    val orderDuration: String = "20 min"
)