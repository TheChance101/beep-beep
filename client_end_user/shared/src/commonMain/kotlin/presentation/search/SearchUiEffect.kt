package presentation.search

sealed class SearchUiEffect {
    data object NavigateToMeal : SearchUiEffect()
    data object NavigateToRestaurant : SearchUiEffect()
}
