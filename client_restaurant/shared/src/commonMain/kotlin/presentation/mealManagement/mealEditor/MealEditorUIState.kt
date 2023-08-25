package presentation.mealManagement.mealEditor

import presentation.mealManagement.CuisineUIState
import presentation.mealManagement.MealDetails

data class MealEditorUIState(
    val id: String = "",
    val meal: MealDetails = MealDetails(),
    val cuisines: List<CuisineUIState> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = "",
    val isCuisinesShow: Boolean = false
)
