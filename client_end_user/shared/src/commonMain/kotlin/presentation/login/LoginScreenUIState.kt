package presentation.login

data class LoginScreenUIState(
    val username: String = "mustafa_sabecsdvdn",
    val password: String = "alpha1!cssc",
    val keepLoggedIn: Boolean = false,
    val isUsernameError: Boolean = false,
    val isPasswordError: Boolean = false,
    val usernameErrorMsg: String = "",
    val passwordErrorMsg: String = "",
)
