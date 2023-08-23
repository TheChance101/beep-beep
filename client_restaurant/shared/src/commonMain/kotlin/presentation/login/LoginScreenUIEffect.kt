package presentation.login

sealed class LoginScreenUIEffect {
    object Login : LoginScreenUIEffect()
    object Permission: LoginScreenUIEffect()
}
