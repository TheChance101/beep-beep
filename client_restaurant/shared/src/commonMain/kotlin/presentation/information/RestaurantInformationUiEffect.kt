package presentation.information

sealed class RestaurantInformationUiEffect {
    object NavigateToLogin: RestaurantInformationUiEffect()
    object NavigateUp: RestaurantInformationUiEffect()
}