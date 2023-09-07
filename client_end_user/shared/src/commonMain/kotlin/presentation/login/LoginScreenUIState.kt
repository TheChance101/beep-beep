package presentation.login

data class LoginScreenUIState(
    val username: String = "",
    val password: String = "",
    val keepLoggedIn: Boolean = false,
    val isUsernameError: Boolean = false,
    val isPasswordError: Boolean = false,
    val usernameErrorMsg: String = "",
    val passwordErrorMsg: String = "",
    val snackbarMessage: String = "",
    val showSnackbar: Boolean = false,
    val isLoading:Boolean = false,
)
