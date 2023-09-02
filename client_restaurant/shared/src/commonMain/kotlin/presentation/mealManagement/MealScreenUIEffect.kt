package presentation.mealManagement

sealed class MealScreenUIEffect {
    object Back : MealScreenUIEffect()
    object MealResponseSuccessfully : MealScreenUIEffect()
    class MealResponseFailed(message: String) : MealScreenUIEffect()

}
