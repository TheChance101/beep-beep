package presentation.login

data class LoginScreenUIState(
    val userName: String = "",
    val password: String = "",
    val keepLoggedIn: Boolean = false,
    val isUsernameError: Boolean = false,
    val isPasswordError: Boolean = false,
    val usernameErrorMsg: String = "",
    val passwordErrorMsg: String = "",
)
