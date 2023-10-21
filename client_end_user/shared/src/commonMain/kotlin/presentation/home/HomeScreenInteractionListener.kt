package presentation.home

import presentation.base.BaseInteractionListener

interface HomeScreenInteractionListener : BaseInteractionListener {
    fun onClickCuisineItem(cuisineId: String)
    fun onClickSeeAllCuisines()
    fun onClickChatSupport()
    fun onClickOrderTaxi()
    fun onClickOrderFood()
    fun onClickOffersSlider(position: Int)
    fun onClickOrderAgain(orderId: String)
    fun onLoginClicked()
    fun onClickCartCard()
    fun onClickRestaurantCard(restaurantId: String)
    fun onClickActiveFoodOrder(orderId: String, tripId: String)
    fun onClickActiveTaxiRide(tripId: String)
}
