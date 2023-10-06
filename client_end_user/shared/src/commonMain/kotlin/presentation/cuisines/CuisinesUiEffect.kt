package presentation.cuisines

sealed interface CuisinesUiEffect{
    data class NavigateToCuisineDetails(val cuisineId: String): CuisinesUiEffect
    data object NavigateBack: CuisinesUiEffect
}