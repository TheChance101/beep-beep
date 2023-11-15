package presentation.restaurants

sealed class RestaurantsUIEffect {
    object NavigateBack : RestaurantsUIEffect()

    data class NavigateToRestaurantDetails(val id: String) : RestaurantsUIEffect()


}
