package presentation.meals.MealsUIState

import domain.entity.Cousin

data class CousinUIState(
    val id: String,
    val name: String,
)
fun List<Cousin>.toCousinUIState(): List<CousinUIState> = map { it.toCousinUIState() }
fun Cousin.toCousinUIState(): CousinUIState {
    return CousinUIState(
        id = id,
        name = name,
    )
}
