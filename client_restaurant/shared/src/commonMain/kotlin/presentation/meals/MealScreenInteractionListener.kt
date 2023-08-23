package presentation.meals

import presentation.base.BaseInteractionListener
import presentation.meals.MealsUIState.CousinUIState

interface MealScreenInteractionListener : BaseInteractionListener {
    fun onClickBack()

    fun onClickMeal()

    fun onClickCousinType(type: CousinUIState)
}