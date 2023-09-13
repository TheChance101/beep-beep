package presentation.auth.signup.registration

data class RegistrationUIState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isUsernameError: Boolean = false,
    val isPasswordError: Boolean = false,
    val usernameErrorMsg: String = "",
    val passwordErrorMsg: String = "",
    val snackbarMessage: String = "",
    val showSnackbar: Boolean = false,
)
