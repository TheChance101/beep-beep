package presentation.restaurantSelection

sealed class RestaurantSelectionScreenUIEffect {
    data object NavigateToMainScreen : RestaurantSelectionScreenUIEffect()
}
