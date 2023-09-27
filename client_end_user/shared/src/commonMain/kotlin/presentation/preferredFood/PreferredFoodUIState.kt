package presentation.preferredFood

import presentation.cuisines.CuisineUiState

data class PreferredFoodUIState(
    val preferredFood : List<CuisineUiState> = emptyList(),
    val selectedPreferredFood :List<String> = emptyList()
)
