package presentation.restaurantSelection

import presentation.base.BaseInteractionListener

interface RestaurantSelectionScreenInteractionListener : BaseInteractionListener {
    fun onRestaurantItemClick(restaurantId: String)

}
