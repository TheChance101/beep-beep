package presentation.home

sealed class HomeScreenUiEffect {
    data class NavigateToCuisineDetails(val cuisineId: String) : HomeScreenUiEffect()
    data object NavigateToCuisines : HomeScreenUiEffect()
}
