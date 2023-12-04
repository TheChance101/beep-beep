package presentation.resturantDetails

import presentation.base.BaseInteractionListener

interface RestaurantInteractionListener : BaseInteractionListener, MealInteractionListener {
    fun onAddToFavourite()
    fun onBack()
    fun onGoToDetails(mealId: String)
    fun onDismissSheet()
    fun onGoToCart()
    fun onDismissDialog()
    fun onClearCart()

    fun onShowLoginSheet()
    fun onShowMealSheet()
    fun onGoToLogin()
    fun onDismissSnackBar()
}
