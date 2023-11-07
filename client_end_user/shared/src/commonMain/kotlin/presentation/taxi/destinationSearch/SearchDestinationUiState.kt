package presentation.taxi.destinationSearch

import presentation.base.ErrorState
import presentation.resturantDetails.MealUIState


data class SearchDestinationUiState(
    val query: String = "",
    val error: ErrorState? = null,
)




