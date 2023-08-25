package presentation.main

import presentation.restaurantSelection.RestaurantUIState

data class MainScreenUIState(
    val revenueChart: ChartItemUiState = ChartItemUiState(),
    val ordersChart: ChartItemUiState = ChartItemUiState(),
    val expanded: Boolean = false,
    val isDropdownMenuOpen: Boolean = false,
    val isOpen: Boolean = true,
    val restaurantName: String = "",
    val restaurants: List<RestaurantUIState> = emptyList(),
    val selectedRestaurantId: String = "",
)

// todo: Need to handle how date look like
data class ChartItemUiState(
    val title: String = "",
    val points: List<Pair<String, Int>> = emptyList(), // string is days in week, int is value
    val totalThisWeek: Int = 0,
    val sign: Char? = null,
)
