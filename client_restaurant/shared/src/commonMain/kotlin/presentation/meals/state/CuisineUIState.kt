package presentation.meals.state

import domain.entity.Cuisine

data class CuisineUIState(
    val id: String,
    val name: String,
)

fun List<Cuisine>.toUIState(): List<CuisineUIState> = map { it.toUIState() }

fun Cuisine.toUIState(): CuisineUIState {
    return CuisineUIState(
        id = id,
        name = name,
    )
}
