package presentation.home

import domain.entity.Cuisine

data class HomeScreenUiState(
    val popularCuisines: List<CuisineUiState> = emptyList(),
    val cuisines: List<CuisineUiState> = emptyList()
)

data class CuisineUiState(
    val cuisineId: String = "",
    val cuisineName: String = "",
    val cuisineImageUrl: String = ""
)

fun Cuisine.toCuisineUiState(): CuisineUiState {
    return CuisineUiState(
        cuisineId = cuisineId,
        cuisineName = cuisineName,
        cuisineImageUrl = cuisineImageUrl,
    )
}

fun List<Cuisine>.toCuisineUiState() = map { it.toCuisineUiState() }