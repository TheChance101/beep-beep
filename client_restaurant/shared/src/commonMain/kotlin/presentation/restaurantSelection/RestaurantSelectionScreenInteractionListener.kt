package presentation.restaurantSelection

import domain.entity.Location
import presentation.base.BaseInteractionListener
import presentation.order.LocationUiSate

interface RestaurantSelectionScreenInteractionListener : BaseInteractionListener {
    fun onClickRestaurant(restaurantId: String, location: LocationUiSate, address:String)

}
