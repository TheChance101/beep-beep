package presentation.restaurants

sealed class RestaurantsUIEffect {
    object onBack : RestaurantsUIEffect()

    object onGoToRestaurantDetails : RestaurantsUIEffect()


}
