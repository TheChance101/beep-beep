package presentation.meals.state

import domain.entity.Cuisine

data class CousinUIState(
    val id: String,
    val name: String,
)
fun List<Cuisine>.toCousinUIState(): List<CousinUIState> = map { it.toCousinUIState() }
fun Cuisine.toCousinUIState(): CousinUIState {
    return CousinUIState(
        id = id,
        name = name,
    )
}
