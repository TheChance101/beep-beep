package presentation.home

sealed class HomeScreenUiEffect {
    data class NavigateToCuisineDetails(val cuisineId: String) : HomeScreenUiEffect()
    data object NavigateToCuisines : HomeScreenUiEffect()
    data object NavigateToChatSupport : HomeScreenUiEffect()
    data object NavigateToOrderTaxi : HomeScreenUiEffect()
    data object ScrollDownToRecommendedRestaurants : HomeScreenUiEffect()
}
