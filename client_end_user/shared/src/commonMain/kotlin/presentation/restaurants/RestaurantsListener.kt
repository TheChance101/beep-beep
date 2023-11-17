package presentation.restaurants

import presentation.base.BaseInteractionListener

interface RestaurantsListener : BaseInteractionListener {

    fun onRestaurantClicked(id: String)

    fun onBackClicked()
}
