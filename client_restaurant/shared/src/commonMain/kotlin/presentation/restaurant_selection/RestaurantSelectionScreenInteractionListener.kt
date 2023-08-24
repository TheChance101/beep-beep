package presentation.restaurant_selection

import presentation.base.BaseInteractionListener

interface RestaurantSelectionScreenInteractionListener : BaseInteractionListener {
    fun onRestaurantItemClick(restaurantId: String)

}