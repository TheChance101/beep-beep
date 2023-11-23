package presentation.main

import presentation.base.BaseInteractionListener
import presentation.order.LocationUiSate

interface MainScreenInteractionListener : BaseInteractionListener {
    fun onClickBack()
    fun onShowMenu()
    fun onDismissMenu()
    fun onRestaurantClicked(restaurantId: String, location: LocationUiSate, address:String)
    fun onAllMealsCardClicked()
    fun onRestaurantInfoCardClicked()
    fun onOrdersCardClicked()
    fun onOrdersHistoryCardClicked()
}
