package presentation.info

sealed class RestaurantInfoUiEffect {
    object ShowNoDataPlaceholder: RestaurantInfoUiEffect()
    data class ShowErrorMessage(val message: String): RestaurantInfoUiEffect()
    data class ShowSaveInfoSuccess(val message: String): RestaurantInfoUiEffect()
    object NavigateToLogin: RestaurantInfoUiEffect()
    object NavigateUp: RestaurantInfoUiEffect()
}