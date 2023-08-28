package presentation.main

import presentation.base.BaseInteractionListener

interface MainScreenInteractionListener : BaseInteractionListener {
    fun onClickBack()
    fun onShowMenu()
    fun onDismissMenu()
    fun onRestaurantClicked(restaurantId: String)
    fun onAllMealsCardClicked()
    fun onRestaurantInfoCardClicked()
    fun onOrdersCardClicked()
    fun onOrdersHistoryCardClicked()
}
