package presentation.login

sealed interface LoginScreenUIEffect{
    data class LoginEffect(val ownerId: String) : LoginScreenUIEffect
    data object LoginUIFailed : LoginScreenUIEffect
}