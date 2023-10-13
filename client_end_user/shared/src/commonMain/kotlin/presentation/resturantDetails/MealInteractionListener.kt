package presentation.resturantDetails

import presentation.base.BaseInteractionListener

interface MealInteractionListener : BaseInteractionListener {
    fun onIncreaseMealQuantity()
    fun onDecreaseMealQuantity()
    fun onAddToCart()
}
