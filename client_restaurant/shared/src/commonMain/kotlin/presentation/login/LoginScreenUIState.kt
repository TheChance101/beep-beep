package presentation.login

data class LoginScreenUIState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String = ""
)
