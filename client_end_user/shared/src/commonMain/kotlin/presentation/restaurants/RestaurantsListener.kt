package presentation.restaurants

import presentation.base.BaseInteractionListener

interface RestaurantsListener : BaseInteractionListener {

    fun onRestaurantClicked()

    fun onBackClicked()
}
