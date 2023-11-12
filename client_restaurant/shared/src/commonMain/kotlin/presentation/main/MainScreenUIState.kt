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
        yAxisData = emptyList(),
        xAxisData = emptyList(),
    ),
    val revenueUiState: ChartsItemUiState = ChartsItemUiState(
        yAxisData = emptyList(),
        xAxisData = emptyList(),
    ),
) {
    val selectedRestaurant: RestaurantUIState
        get() = restaurants.firstOrNull { it.id == selectedRestaurantId } ?: RestaurantUIState()
}

data class ChartsItemUiState(
    val yAxisData: List<Double> = listOf(0.5 , 0.8 , 0.4 , 0.2 , 0.1 , 0.18 , 0.25),
    val xAxisData: List<String> = listOf("sun" , "mon" , "thus" , "wens" , "thurs" , "Fri" ,"Sat"),
)

fun List<Pair<String, Double>>.toChartsItemUiState(): ChartsItemUiState {
    val yAxisData = this.map { it.second }
    val xAxisData = this.map { it.first }
    return ChartsItemUiState(
        yAxisData = yAxisData,
        xAxisData = xAxisData,
    )
}



