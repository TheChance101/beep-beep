package presentation.home

sealed class HomeScreenUiEffect {
    data class NavigateToCuisineDetails(val cuisineId: String) : HomeScreenUiEffect()
    data class NavigateToOrderDetails(val orderId: String) : HomeScreenUiEffect()
    data object NavigateToCuisines : HomeScreenUiEffect()
    data object NavigateToChatSupport : HomeScreenUiEffect()
    data object NavigateToOrderTaxi : HomeScreenUiEffect()
    data object ScrollDownToRecommendedRestaurants : HomeScreenUiEffect()
    data class NavigateToOfferItem(val offerId : String) : HomeScreenUiEffect()
    data object NavigateToSearch: HomeScreenUiEffect()
    data object NavigateToCart: HomeScreenUiEffect()
    data object NavigateLoginScreen: HomeScreenUiEffect()
}