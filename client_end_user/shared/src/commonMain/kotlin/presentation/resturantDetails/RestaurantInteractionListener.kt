package presentation.resturantDetails

import presentation.base.BaseInteractionListener

interface RestaurantInteractionListener :BaseInteractionListener  {

    fun onAddToFavourite()

    fun onBack()

    fun onGoToDetails(mealId: String)

    fun onDismissSheet()

    fun onShowSheet()

    fun onGoToCart()

    fun onShowMealSheet()

    fun onGoToLogin()

    fun onIncressQuantity()

    fun onDecressQuantity()

}