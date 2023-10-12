package presentation.meals

import presentation.base.BaseInteractionListener
import presentation.resturantDetails.MealInteractionListener

interface MealsInteractionListener : BaseInteractionListener, MealInteractionListener {
    fun onDismissSheet()

    fun onBackClicked()
}
