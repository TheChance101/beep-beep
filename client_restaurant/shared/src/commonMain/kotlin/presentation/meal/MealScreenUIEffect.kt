package presentation.meal

sealed class MealScreenUIEffect {
    object Back : MealScreenUIEffect()
    object MealResponseSuccessfully : MealScreenUIEffect()
}
