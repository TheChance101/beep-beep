package presentation.resturantDetails

import presentation.base.BaseInteractionListener

interface RestaurantInteractionListener :BaseInteractionListener  {

    fun onAddToFavourite()

    fun onBack()

    fun onGoToDetails()

    fun onDismissSheet()

    fun onShowSheet()

    fun onGoToLogin()

}