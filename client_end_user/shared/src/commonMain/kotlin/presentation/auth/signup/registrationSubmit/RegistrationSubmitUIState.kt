package presentation.auth.signup.registrationSubmit

data class RegistrationSubmitUIState(
    val fullName: String = "",
    val email: String = "",
    val phone: String = "",
    val fullErrorMsg: String = "",
    val emailErrorMsg: String = "",
    val phoneErrorMsg: String = "",
    val snackbarMessage: String = "",
    val showSnackbar: Boolean = false,
    val isFullNameError: Boolean = false,
    val isEmailError: Boolean = false,
    val isPhoneError: Boolean = false,
    val isLoading: Boolean = false,
)
