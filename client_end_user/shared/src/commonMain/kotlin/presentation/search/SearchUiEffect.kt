package presentation.search

sealed class SearchUiEffect {
    data class NavigateToMeal(val mealId: String) : SearchUiEffect()
    data class NavigateToRestaurant(val restaurantId: String) : SearchUiEffect()
}
