package presentation.search

import presentation.home.RestaurantUiState

data class SearchUiState(
    val query: String = "",
    val meals: List<MealUiState> = emptyList(),
    val restaurants: List<RestaurantUiState> = emptyList()
)


data class MealUiState(
    val id: String = "",
    val name: String = "",
    val restaurantName: String = "",
    val price: String = ""
)

data class RestaurantUiState(
    val id: String = "",
    val image: String = "",
    val name: String = ""
)
