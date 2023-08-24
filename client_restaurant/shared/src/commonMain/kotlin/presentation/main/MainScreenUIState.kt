package presentation.main

import androidx.compose.ui.graphics.Color

data class MainScreenUIState(
    val restaurantName: String = "",
    val isOpen:Boolean = true,
    val isDropdownMenuOpen:Boolean = false,
    val charts: List<ChartItemUiState> = emptyList(),
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