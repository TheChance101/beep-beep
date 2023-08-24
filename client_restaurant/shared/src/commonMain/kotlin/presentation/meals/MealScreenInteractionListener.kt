package presentation.meals

import presentation.base.BaseInteractionListener
import presentation.meals.state.CousinUIState

interface MealScreenInteractionListener : BaseInteractionListener {
    fun onClickBack()

    fun onClickCousinType(type: CousinUIState)

    fun onAddMeaClick()
}
