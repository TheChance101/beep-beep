package presentation.main

import com.aay.compose.barChart.model.BarParameters
import com.aay.compose.lineChart.model.LineParameters
import domain.entity.BarsParameters
import domain.entity.LinesParameters
import presentation.base.ErrorState
import presentation.restaurantSelection.RestaurantUIState

data class MainScreenUIState(
    val expanded: Boolean = false,
    val isLoading: Boolean = false,
    val error: ErrorState? = null,
    val restaurants: List<RestaurantUIState> = emptyList(),
    val selectedRestaurantId: String = "",
    val ordersChart: ChartsItemUiState = ChartsItemUiState(
        barsParameters = emptyList(),
        xAxisData = emptyList(),
    ),
    val revenueChart: ChartsItemUiState = ChartsItemUiState(
        linesParameters = emptyList(),
        xAxisData = emptyList(),
    ),
) {
    val selectedRestaurant: RestaurantUIState
        get() = restaurants.firstOrNull { it.id == selectedRestaurantId } ?: RestaurantUIState()
}

// todo: Need to handle how date look like
data class ChartsItemUiState(
    val linesParameters: List<LineParameters> = emptyList(),
    val barsParameters: List<BarParameters> = emptyList(),
    val xAxisData: List<String> = emptyList()
)



fun LinesParameters.toChartsItemUiState() = ChartsItemUiState(
    linesParameters = linesParameters,
    xAxisData = xAxisData
)

fun BarsParameters.toChartsItemUiState() = ChartsItemUiState(
    barsParameters = barsParameters,
    xAxisData = xAxisData
)