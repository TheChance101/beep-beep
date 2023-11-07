package presentation.cuisines

import domain.entity.Cuisine

fun Cuisine.toCuisineUiState(): CuisineUiState {
    return CuisineUiState(
        cuisineId = id,
        cuisineName = name,
        cuisineImageUrl = imageUrl,
    )
}

fun List<Cuisine>.toCuisineUiState() = map { it.toCuisineUiState() }
