package presentation.information

sealed class RestaurantInformationUiEffect {

    object LogoutSuccess : RestaurantInformationUiEffect()

    object NavigateUp : RestaurantInformationUiEffect()

    object ShowNoInternetError : RestaurantInformationUiEffect()

    object ShowUnknownError : RestaurantInformationUiEffect()

    object UpdateInformationSuccess : RestaurantInformationUiEffect()

}