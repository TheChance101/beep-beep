package presentation.main

import androidx.compose.ui.graphics.Color
import presentation.restaurantSelection.RestaurantUIState

data class MainScreenUIState(
    val charts: List<ChartItemUiState> = emptyList(),
    val expanded: Boolean = false,
    val isDropdownMenuOpen: Boolean = false,
    val isOpen: Boolean = true,
    val restaurantName: String = "",
    val restaurants: List<RestaurantUIState> = emptyList(),
    val selectedRestaurantId: String = "",
)



data class OptionItemUiState(
    val title: String,
    val imagePath: String,
    val color: Color,
    val index: Int,
)

// todo: Need to handle how date look like
data class ChartItemUiState(
    val title: String,
    val points: List<Pair<String, Int>>, // string is days in week, int is value
    val totalThisWeek: Int,
    val sign: Char? = null,
    val isRevenue: Boolean,
)
