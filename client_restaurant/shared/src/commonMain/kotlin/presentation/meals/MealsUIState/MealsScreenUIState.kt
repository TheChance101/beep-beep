package presentation.meals.MealsUIState

import domain.entity.Cousin
import domain.entity.Meal
import presentation.meals.MealsUIState.CousinUIState
import presentation.meals.MealsUIState.MealUIState

data class MealsScreenUIState(
    val cousin: List<CousinUIState> = emptyList(),
    val meals: List<MealUIState> = emptyList(),
    val selectedCousin: CousinUIState? = CousinUIState("", "All"),
    val isLoading: Boolean = false,
    val error: Throwable? = null,
)



