package presentation.meals

import presentation.base.BaseInteractionListener

interface MealScreenInteractionListener : BaseInteractionListener {
    fun onClickBack()

    fun onClickMeal()

    fun onClickCousinType(type: MealsScreenUIState.CousinUIState)
}