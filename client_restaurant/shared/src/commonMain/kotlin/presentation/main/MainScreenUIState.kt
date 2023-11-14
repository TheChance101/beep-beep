package presentation.main

import presentation.base.ErrorState
import presentation.restaurantSelection.RestaurantUIState

data class MainScreenUIState(
    val expanded: Boolean = false,
    val isLoading: Boolean = false,
    val error: ErrorState? = null,
    val restaurants: List<RestaurantUIState> = emptyList(),
    val selectedRestaurantId: String = "",
    val ordersCountStatistics: ChartsItemUiState = ChartsItemUiState(),
    val revenueStatistics: ChartsItemUiState = ChartsItemUiState(),
) {
    val totalOrders: Int = ordersCountStatistics.yAxisData.sum().toInt()
    val totalOrderReturns: String =
        "$${revenueStatistics.yAxisData.sum()}"
    val selectedRestaurant: RestaurantUIState
        get() = restaurants.firstOrNull { it.restaurantId == selectedRestaurantId }
            ?: RestaurantUIState()
}

data class ChartsItemUiState(
    val yAxisData: List<Double> = listOf(0.0),
    val xAxisData: List<String> = listOf(""),
)

fun List<Pair<String, Double>>.toChartsItemUiState(): ChartsItemUiState {
    val xAxisData = this.map { it.first } // weeks
    val yAxisData = this.map { it.second } // values
    return ChartsItemUiState(
        yAxisData = yAxisData,
        xAxisData = xAxisData,
    )
}



