package presentation.home

sealed class HomeScreenUiEffect {
    data class CuisineUiEffect(val cuisineId: String) : HomeScreenUiEffect()
    data object SeeAllCuisineUiEffect : HomeScreenUiEffect()
}
