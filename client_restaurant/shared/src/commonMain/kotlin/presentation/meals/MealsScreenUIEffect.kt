package presentation.meals

sealed class MealsScreenUIEffect {

    object Back : MealsScreenUIEffect()

    class NavigateToMealDetails(val mealId: String) : MealsScreenUIEffect()

    class NavigateToAddMeal (val restaurantId: String) : MealsScreenUIEffect()

}
