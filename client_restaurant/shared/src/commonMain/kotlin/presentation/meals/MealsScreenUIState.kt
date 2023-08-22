package presentation.meals

import data.remote.model.CousinDto
import data.remote.model.toEntity
import domain.entity.Cousin

data class MealsScreenUIState(
    val cousin: List<CousinUIState> = emptyList(),
    val meals: List<MealUIState> = emptyList(),
    val selectedCousin: CousinUIState? = CousinUIState("", "All"),
    val isLoading: Boolean = false,
    val error: Throwable? = null,
){
    data class CousinUIState(
        val id: String,
        val name: String,
    )
    data class  MealUIState(
        val id: String,
        val name: String,
        val price: Double,
        val imageUrl: String,
    )
}
fun List<Cousin>.toUIState(): List<MealsScreenUIState.CousinUIState > = map { it.toUIState() }
fun Cousin.toUIState(): MealsScreenUIState.CousinUIState {
    return MealsScreenUIState.CousinUIState(
        id = id,
        name = name,
    )
}