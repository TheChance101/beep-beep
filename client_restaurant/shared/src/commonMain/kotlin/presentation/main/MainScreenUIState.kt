package presentation.main

import presentation.restaurantSelection.RestaurantUIState

data class MainScreenUIState(
    val expanded: Boolean = false,
    val restaurants: List<RestaurantUIState> = emptyList(),
    val selectedRestaurantId: String = "",
    val ordersChart: ChartItemUiState = ChartItemUiState(
        title = "Orders",
        points = emptyList(),
        totalThisWeek = 280,
        sign = null,
    ),
    val revenueChart: ChartItemUiState = ChartItemUiState(
        title = "Revenue",
        points = emptyList(),
        totalThisWeek = 4700,
        sign = '$',
    ),
) {
    val selectedRestaurant: RestaurantUIState
        get() = restaurants.firstOrNull { it.id == selectedRestaurantId } ?: RestaurantUIState()
}

// todo: Need to handle how date look like
data class ChartItemUiState(
    val title: String = "",
    val points: List<Pair<String, Int>> = emptyList(), // string is days in week, int is value
    val totalThisWeek: Int = 0,
    val sign: Char? = null,
)
