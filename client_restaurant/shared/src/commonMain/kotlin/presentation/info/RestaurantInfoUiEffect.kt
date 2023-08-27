package presentation.info

sealed class RestaurantInfoUiEffect {
    object NavigateToLogin: RestaurantInfoUiEffect()
    object NavigateUp: RestaurantInfoUiEffect()
}