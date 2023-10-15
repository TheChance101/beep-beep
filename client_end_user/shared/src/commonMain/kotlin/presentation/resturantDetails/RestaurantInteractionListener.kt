package presentation.resturantDetails

import presentation.base.BaseInteractionListener

interface RestaurantInteractionListener : BaseInteractionListener, MealInteractionListener {
    fun onAddToFavourite()
    fun onBack()
    fun onGoToDetails(mealId: String)
    fun onDismissSheet()
    fun onShowLoginSheet()
    fun onShowMealSheet()
    fun onGoToLogin()
}
