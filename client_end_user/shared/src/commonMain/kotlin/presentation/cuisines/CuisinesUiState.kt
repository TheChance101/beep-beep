package presentation.cuisines

data class CuisinesUiState(
    val cuisines: List<CuisineUiState> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = "",
)

data class CuisineUiState(
    val cuisineId: String = "",
    val cuisineName: String = "",
    val cuisineImageUrl: String = ""
)

