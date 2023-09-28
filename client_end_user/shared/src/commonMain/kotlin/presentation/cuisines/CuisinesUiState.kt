package presentation.cuisines

import presentation.base.ErrorState

data class CuisinesUiState(
    val cuisines: List<CuisineUiState> = emptyList(),
    val isLoading: Boolean = false,
    val error: ErrorState? = null,
)

data class CuisineUiState(
    val cuisineId: String = "",
    val cuisineName: String = "",
    val cuisineImageUrl: String = ""
)

