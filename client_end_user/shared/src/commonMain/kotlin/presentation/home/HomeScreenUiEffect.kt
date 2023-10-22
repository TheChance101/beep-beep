package presentation.home

sealed class HomeScreenUiEffect {
    data class NavigateToMeals(
        val cuisineId: String,
        val cuisineName: String,
    ) : HomeScreenUiEffect()

    data class NavigateToOrderDetails(val orderId: String) : HomeScreenUiEffect()
    data object NavigateToCuisines : HomeScreenUiEffect()
    data object NavigateToChatSupport : HomeScreenUiEffect()
    data object NavigateToOrderTaxi : HomeScreenUiEffect()
    data object ScrollDownToRecommendedRestaurants : HomeScreenUiEffect()
    data class NavigateToOfferItem(val offerId: String) : HomeScreenUiEffect()
    data object NavigateToCart : HomeScreenUiEffect()
    data object NavigateLoginScreen : HomeScreenUiEffect()
    data class NavigateToRestaurantDetails(val restaurantId: String) : HomeScreenUiEffect()
    data class NavigateToTrackOrder(val orderId: String, val tripId: String) :
        HomeScreenUiEffect()

    data class NavigateToTrackTaxiRide(val tripId: String) : HomeScreenUiEffect()
}
