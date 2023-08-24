package presentation.meals.state

data class MealsScreenUIState(
    val cousin: List<CousinUIState> = emptyList(),
    val meals: List<MealUIState> = emptyList(),
    val selectedCousin: CousinUIState? = CousinUIState("", "All"),
    val isLoading: Boolean = false,
    val error: Throwable? = null,
)



