package presentation.mealManagement.mealCreation

import presentation.mealManagement.CuisineUIState
import presentation.mealManagement.MealUIState

data class MealCreationUIState(
    val meal: MealUIState = MealUIState(),
    val cuisines: List<CuisineUIState> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = "",
    val isCuisinesShow:Boolean= false
)
