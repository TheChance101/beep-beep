package presentation.cuisines

sealed interface CuisinesUiEffect {
    data class NavigateToCuisineDetails(val cuisineId: String, val cuisineName: String) :
        CuisinesUiEffect

    data object NavigateBack : CuisinesUiEffect
}
