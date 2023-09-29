package presentation.main

import presentation.base.ErrorState
import presentation.restaurantSelection.RestaurantUIState

data class MainScreenUIState(
    val expanded: Boolean = false,
    val isLoading: Boolean = false,
    val error: ErrorState? = null,
    val restaurants: List<RestaurantUIState> = emptyList(),
    val selectedRestaurantId: String = "",

    val orderUiState: ChartsItemUiState = ChartsItemUiState(
        yAxisData = listOf(0.0,0.0),
        xAxisData = listOf("1","2"),
        label = "Orders"
    ),
    val revenueUiState: ChartsItemUiState = ChartsItemUiState(
        yAxisData = listOf(0.0,0.0),
        xAxisData = listOf("1","2"),
        label = "Revenue"
    )
) {
    val selectedRestaurant: RestaurantUIState
        get() = restaurants.firstOrNull { it.id == selectedRestaurantId } ?: RestaurantUIState()
}

data class ChartsItemUiState(
    val yAxisData: List<Double> = emptyList(),
    val xAxisData: List<String> = emptyList(),
    val label : String = "",
    )

fun List<Pair<String, Double>>.toChartsItemUiState(label:String): ChartsItemUiState {
    val yAxisData = this.map { it.second }
    val xAxisData = this.map { it.first }
    return ChartsItemUiState(yAxisData, xAxisData,label)
}



