package presentation.login

sealed interface LoginScreenUIEffect {
    data object OnNavigateToRestaurantScreenSelection : LoginScreenUIEffect
    data object OnNavigateToMainScreen : LoginScreenUIEffect

}
