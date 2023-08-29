package presentation.mealManagement

import presentation.composables.ModalBottomSheetState

data class MealEditorUIState(
    val id: String = "",
    val meal: MealDetails = MealDetails(),
    val cuisines: List<CuisineUIState> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = "",
    val isCuisinesShow: Boolean = false,
    val sheetState: ModalBottomSheetState = ModalBottomSheetState()
)
