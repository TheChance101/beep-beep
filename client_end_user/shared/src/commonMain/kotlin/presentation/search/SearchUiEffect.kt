package presentation.search

sealed class SearchUiEffect {
    data class NavigateToRestaurant(val restaurantId: String) : SearchUiEffect()
}
