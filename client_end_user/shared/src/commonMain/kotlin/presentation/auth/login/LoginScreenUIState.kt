package presentation.auth.login

data class LoginScreenUIState(
    val username: String = "",
    val password: String = "",
    val keepLoggedIn: Boolean = false,
    val isUsernameError: Boolean = false,
    val isPasswordError: Boolean = false,
    val snackbarMessage: String = "",
    val showSnackbar: Boolean = false,
    val isLoading:Boolean = false,
)
