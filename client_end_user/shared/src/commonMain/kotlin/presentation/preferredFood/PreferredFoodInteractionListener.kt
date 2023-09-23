package presentation.preferredFood

import presentation.base.BaseInteractionListener

interface PreferredFoodInteractionListener :BaseInteractionListener {
    fun onPreferredFoodClicked(id: String)
}