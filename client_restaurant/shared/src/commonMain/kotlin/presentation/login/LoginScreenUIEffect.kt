package presentation.login

sealed class LoginScreenUIEffect {
    data class LoginEffect(val ownerId: String) : LoginScreenUIEffect()
}
