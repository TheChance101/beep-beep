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
    val ordersChart: BarChartItemUiState = BarChartItemUiState(
        barsParameters = emptyList(),
        xAxisData = emptyList(),
    ),
    val revenueChart: LineChartItemUiState = LineChartItemUiState(
        linesParameters = emptyList(),
        xAxisData = emptyList(),
    ),
) {
    val selectedRestaurant: RestaurantUIState
        get() = restaurants.firstOrNull { it.id == selectedRestaurantId } ?: RestaurantUIState()
}

// todo: Need to handle how date look like
data class LineChartItemUiState(
    val linesParameters: List<LineParameters> = emptyList(),
    val xAxisData: List<String> = emptyList()
)

data class BarChartItemUiState(
    val barsParameters: List<BarParameters> = emptyList(),
    val xAxisData: List<String> = emptyList()
)

fun LinesParameters.toLineChartItemUiState() = LineChartItemUiState(
    linesParameters = linesParameters,
    xAxisData = xAxisData
)

fun BarsParameters.toBarChartItemUiState() = BarChartItemUiState(
    barsParameters = barsParameters,
    xAxisData = xAxisData
)