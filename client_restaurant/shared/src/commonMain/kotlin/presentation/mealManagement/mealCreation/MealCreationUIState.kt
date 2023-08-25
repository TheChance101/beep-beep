package presentation.mealManagement.mealCreation

import presentation.mealManagement.CuisineUIState
import presentation.mealManagement.MealDetails

data class MealCreationUIState(
    val meal: MealDetails = MealDetails(),
    val cuisines: List<CuisineUIState> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = "",
    val isCuisinesShow:Boolean= false
)
