package presentation.auth.login

sealed class LoginScreenUIEffect {
    data object NavigateToHome : LoginScreenUIEffect()
    data object NavigateToSignup : LoginScreenUIEffect()
}
