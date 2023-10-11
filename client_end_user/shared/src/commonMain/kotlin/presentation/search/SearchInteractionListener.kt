package presentation.search

import presentation.base.BaseInteractionListener
import presentation.resturantDetails.MealUIState

interface SearchInteractionListener : BaseInteractionListener {
    fun onSearchInputChanged(keyword: String)
    fun onRestaurantClicked(restaurantId: String)
    fun onMealClicked(meal: MealUIState)
    fun onDismissSheet()
}
