package presentation.meals

import presentation.base.ErrorState
import presentation.resturantDetails.MealUIState


data class MealsUiState(
    val cuisineName: String = "",
    val meals: List<MealUIState> = emptyList(),

    val showMealSheet: Boolean = false,
    val selectedMeal: MealUIState = MealUIState(),

    val isLoading: Boolean = false,
    val error: ErrorState? = null,
)
