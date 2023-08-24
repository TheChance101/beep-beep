package presentation.restaurant_selection

import util.isRestaurantOpen


data class RestaurantSelectionScreenUIState(
    val restaurants: List<Restaurant> = emptyList()
) {
    data class Restaurant(
        val id: String,
        val restaurantName: String,
        val restaurantNumber: String,
        val isOpen: Boolean = false
    )
}

fun domain.entity.Restaurant.toUi(): RestaurantSelectionScreenUIState.Restaurant {
    return RestaurantSelectionScreenUIState.Restaurant(
        id = this.id,
        restaurantName = this.name,
        restaurantNumber = this.phone,
        isOpen = isRestaurantOpen(this.openingTime, this.closingTime)
    )
}