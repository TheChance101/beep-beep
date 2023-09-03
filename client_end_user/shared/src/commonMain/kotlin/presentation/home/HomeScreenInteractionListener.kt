package presentation.home

import presentation.base.BaseInteractionListener

interface HomeScreenInteractionListener : BaseInteractionListener {
    fun onClickCuisineItem(cuisineId: String)
    fun onclickSeeAllCuisines()
}