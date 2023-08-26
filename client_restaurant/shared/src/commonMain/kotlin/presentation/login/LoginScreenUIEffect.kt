package presentation.login

sealed class LoginScreenUIEffect {
    object LoginUIFailed : LoginScreenUIEffect()
    data class LoginEffect(val ownerId: String) : LoginScreenUIEffect()
}
