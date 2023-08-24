package presentation.restaurantSelection

sealed class RestaurantSelectionScreenUIEffect {
    data class SelectRestaurant(val restaurantId : String) : RestaurantSelectionScreenUIEffect()
}
