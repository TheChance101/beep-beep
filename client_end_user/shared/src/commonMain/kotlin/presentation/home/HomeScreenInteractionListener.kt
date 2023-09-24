package presentation.home

import presentation.base.BaseInteractionListener

interface HomeScreenInteractionListener : BaseInteractionListener {
    fun onClickCuisineItem(cuisineId: String)
    fun onclickSeeAllCuisines()
    fun onClickChatSupport()
    fun onClickOrderTaxi()
    fun onClickOrderFood()
    fun onClickOffersSlider(position : Int)
    fun onClickSearch()
    fun onClickOrderAgain(orderId : String)
    fun onLoginClicked()
    fun onClickCartCard()
}