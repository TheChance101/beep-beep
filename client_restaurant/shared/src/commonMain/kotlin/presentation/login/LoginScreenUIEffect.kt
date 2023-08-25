package presentation.login

sealed class LoginScreenUIEffect {
    data class Login(val ownerId: String) : LoginScreenUIEffect()
    object Login : LoginScreenUIEffect()
    object Permission: LoginScreenUIEffect()
}
