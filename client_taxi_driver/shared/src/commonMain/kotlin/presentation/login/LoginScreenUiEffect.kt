package presentation.login

sealed interface LoginScreenUIEffect {
    data class LoginEffect(val driverId: String) : LoginScreenUIEffect
    data object LoginUIFailed : LoginScreenUIEffect

}