package presentation.restaurant_selection

sealed class RestaurantSelectionScreenUIEffect {
    data class SelectRestaurant(val restaurantId : String) : RestaurantSelectionScreenUIEffect()
}