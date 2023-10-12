package presentation.search

import presentation.base.BaseInteractionListener
import presentation.resturantDetails.MealInteractionListener
import presentation.resturantDetails.MealUIState

interface SearchInteractionListener : BaseInteractionListener , MealInteractionListener{
    fun onSearchInputChanged(keyword: String)
    fun onRestaurantClicked(restaurantId: String)
    fun onMealClicked(meal: MealUIState)
    fun onDismissSheet()
}
