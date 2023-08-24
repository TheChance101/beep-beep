package presentation.meals

sealed class MealsScreenUIEffect {

    object Back : MealsScreenUIEffect()

    object NavigateToMealDetails : MealsScreenUIEffect()

    object NavigateToAddMeal : MealsScreenUIEffect()

}
