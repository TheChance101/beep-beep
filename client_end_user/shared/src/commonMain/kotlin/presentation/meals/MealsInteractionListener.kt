package presentation.meals

import presentation.base.BaseInteractionListener
import presentation.resturantDetails.MealInteractionListener
import presentation.resturantDetails.MealUIState

interface MealsInteractionListener : BaseInteractionListener, MealInteractionListener {
    fun onMealClicked(meal: MealUIState)
    fun onDismissSheet()
    fun onBackClicked()
    fun onLoginClicked()
}
