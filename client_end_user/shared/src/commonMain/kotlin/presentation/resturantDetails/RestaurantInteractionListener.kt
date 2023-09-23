package presentation.resturantDetails

import presentation.base.BaseInteractionListener

interface RestaurantInteractionListener :BaseInteractionListener  {

    fun onAddToFavourite()

    fun onBack()

    fun onGoToDetails()

}