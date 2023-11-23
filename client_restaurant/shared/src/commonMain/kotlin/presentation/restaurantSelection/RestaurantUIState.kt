package presentation.restaurantSelection

import presentation.order.LocationUiSate
import presentation.order.toUiState

data class RestaurantScreenUIState(
    val isLoading: Boolean = false,
    val error: String = "",
    val restaurants: List<RestaurantUIState> = emptyList(),
)

data class RestaurantUIState(
    val restaurantId: String = "",
    val restaurantName: String = "",
    val location: LocationUiSate= LocationUiSate(),
    val address: String="",
    val restaurantPhoneNumber: String = "",
    val priceLevel: String = "",
    val isOpen: Boolean = false,
)

fun domain.entity.Restaurant.toUiState() = RestaurantUIState(
    restaurantId = id,
    restaurantName = name,
    restaurantPhoneNumber = phone,
    priceLevel = priceLevel,
    isOpen = isRestaurantOpen(),
    location = location.toUiState(),
    address = address,
)


fun List<domain.entity.Restaurant>.toUiState() = map { it.toUiState() }
