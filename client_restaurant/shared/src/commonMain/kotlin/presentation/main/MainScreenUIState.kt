package presentation.main

import domain.entity.DataSet
import domain.entity.Statistics
import presentation.base.ErrorState
import presentation.restaurantSelection.RestaurantUIState

data class MainScreenUIState(
    val expanded: Boolean = false,
    val isLoading: Boolean = false,
    val error: ErrorState? = null,
    val restaurants: List<RestaurantUIState> = emptyList(),
    val selectedRestaurantId: String = "",

    val chartsItemUiState: ChartsItemUiState = ChartsItemUiState(
        dataSets = emptyList(),
        xAxisData = emptyList()
    )
) {
    val selectedRestaurant: RestaurantUIState
        get() = restaurants.firstOrNull { it.id == selectedRestaurantId } ?: RestaurantUIState()
}

data class ChartsItemUiState(
    val dataSets: List<DataSet> = emptyList(),
    val xAxisData: List<String> = emptyList(),
    )


fun Statistics.toChartsItemUiState() = ChartsItemUiState(
    dataSets = dataSets,
    xAxisData = xAxisData
)
