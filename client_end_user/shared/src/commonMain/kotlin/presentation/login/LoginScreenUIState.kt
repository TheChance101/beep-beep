package presentation.login

data class LoginScreenUIState(
    val username: String = "ahmed_nasser_zaza",
    val password: String = "12345678aA@",
    val keepLoggedIn: Boolean = false,
    val isUsernameError: Boolean = false,
    val isPasswordError: Boolean = false,
    val usernameErrorMsg: String = "",
    val passwordErrorMsg: String = "",
)
