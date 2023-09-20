package presentation.cuisines

import presentation.base.BaseInteractionListener

interface CuisinesInteractionListener : BaseInteractionListener{
    fun onCuisineClicked(cuisineId: String)
    fun onBackIconClicked()
}