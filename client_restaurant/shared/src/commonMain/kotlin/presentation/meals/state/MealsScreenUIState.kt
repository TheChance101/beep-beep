package presentation.meals.state

data class MealsScreenUIState(
    val cuisine: List<CuisineUIState> = emptyList(),
    val meals: List<MealUIState> = emptyList(),
    val selectedCuisine: CuisineUIState? = CuisineUIState("", "All"),
    val isLoading: Boolean = false,
    val error: Throwable? = null,
)



