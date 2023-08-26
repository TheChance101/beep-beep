package presentation.restaurantSelection




data class RestaurantScreenUIState(
    val isLoading: Boolean = false,
    val error: String = "",
    val restaurants: List<RestaurantUIState> = emptyList()
)

data class RestaurantUIState(
    val id: String = "",
    val restaurantName: String = "",
    val restaurantNumber: String = "",
    val isOpen: Boolean = false
)

fun domain.entity.Restaurant.toUiState() = RestaurantUIState(
    id = id,
    restaurantName = name,
    restaurantNumber = phone,
    isOpen = isRestaurantOpen()
)


fun List<domain.entity.Restaurant>.toUiState() = map { it.toUiState() }
