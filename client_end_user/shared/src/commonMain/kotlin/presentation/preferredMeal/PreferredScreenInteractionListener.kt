package presentation.preferredMeal

import presentation.base.BaseInteractionListener

interface PreferredScreenInteractionListener : BaseInteractionListener {
    fun onClickPreferredMeal(priceLevel: String)
}