package presentation.home

data class HomeScreenUiState(
    val cuisines: List<CuisineUiState> = emptyList()
)

data class CuisineUiState(
    val cuisineName: String = "",
    val cuisineImageUrl: String = ""
)
