package presentation.meals

sealed class MealsScreenUIEffect {

    object Back : MealsScreenUIEffect()

    class NavigateToMealDetails(val mealId: String) : MealsScreenUIEffect()

    object NavigateToAddMeal : MealsScreenUIEffect()

}
