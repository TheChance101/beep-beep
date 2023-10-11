package presentation.search

import presentation.base.BaseInteractionListener

interface SearchInteractionListener : BaseInteractionListener {
    fun onSearchInputChanged(keyword: String)
    fun onRestaurantClicked(restaurantId: String)
    fun onMealClicked(mealId: String)
    fun onDismissSheet()
}
