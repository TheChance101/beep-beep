package presentation.restaurantSelection

import presentation.base.BaseInteractionListener

interface RestaurantSelectionScreenInteractionListener : BaseInteractionListener {
    fun onClickRestaurant(restaurantId: String)

}
