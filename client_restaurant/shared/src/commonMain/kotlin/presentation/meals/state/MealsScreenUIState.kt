package presentation.meals.state

import presentation.base.ErrorState

data class MealsScreenUIState(
    val cuisines: List<CuisineUIState> = emptyList(),
    val meals: List<MealUIState> = emptyList(),
    val selectedCuisine: CuisineUIState = CuisineUIState(),
    val isLoading: Boolean = false,
    val error: ErrorState? = null,
)



