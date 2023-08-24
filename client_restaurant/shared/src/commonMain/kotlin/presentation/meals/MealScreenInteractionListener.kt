package presentation.meals

import presentation.base.BaseInteractionListener
import presentation.meals.state.CousinUIState

interface MealScreenInteractionListener : BaseInteractionListener {
    fun onClickBack()

    fun onClickMeal(mealId: String)

    fun onClickCousinType(type: CousinUIState)

    fun onAddMeaClick()
}
