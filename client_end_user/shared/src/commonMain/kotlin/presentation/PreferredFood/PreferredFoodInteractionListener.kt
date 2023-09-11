package presentation.PreferredFood

import presentation.base.BaseInteractionListener

interface PreferredFoodInteractionListener :BaseInteractionListener {
    fun onPreferredFoodClicked(foodUIState: FoodUIState)
}