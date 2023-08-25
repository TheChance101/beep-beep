package presentation.login

sealed class LoginScreenUIEffect {
    data class Login(val ownerId: String) : LoginScreenUIEffect()
}
