package presentation.auth.signup.registrationSubmit

data class RegistrationSubmitUIState(
    val fullName: String = "",
    val phone: String = "",
    val address: String = "",
    val snackbarMessage: String = "",
    val showSnackbar: Boolean = false,
    val isFullNameError: Boolean = false,
    val isPhoneError: Boolean = false,
    val isAddressError: Boolean = false,
    val isLoading: Boolean = false,
    val languageCode: String = "us",
)
