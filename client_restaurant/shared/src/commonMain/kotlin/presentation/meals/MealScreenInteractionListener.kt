package presentation.meals

import presentation.base.BaseInteractionListener
import presentation.meals.MealsUIState.CousinUIState

interface MealScreenInteractionListener : BaseInteractionListener {
    fun onClickBack()


    fun onClickCousinType(type: CousinUIState)
}