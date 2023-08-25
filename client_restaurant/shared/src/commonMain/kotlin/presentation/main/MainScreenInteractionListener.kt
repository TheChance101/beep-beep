package presentation.main

import presentation.base.BaseInteractionListener

interface MainScreenInteractionListener : BaseInteractionListener {
    fun onClickBack()
    fun onShowMenu()
    fun onDismissMenu()
    fun onRestaurantClick(restaurantId: String)
    fun onCardClick(cardIndex: Int)
}
