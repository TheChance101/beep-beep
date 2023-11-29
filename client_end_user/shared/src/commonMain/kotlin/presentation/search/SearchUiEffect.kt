package presentation.search

sealed interface SearchUiEffect {
    data class NavigateToRestaurant(val restaurantId: String) : SearchUiEffect
    data object NavigateToLogin : SearchUiEffect
    data object onGoToCart : SearchUiEffect
}
