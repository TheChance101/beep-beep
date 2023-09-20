package presentation.profile

data class ProfileUIState(
    val user: UserUIState = UserUIState(),
    val fullName: String = "",
    val phoneNumber: String = "",
    val snackBarMessage: String = "",
    val showSnackBar: Boolean = false,
    val isFullNameError: Boolean = false,
    val isPhoneNumberError: Boolean = false,
    val fullNameErrorMsg: String = "",
    val mobileNumberErrorMsg: String = "",
    val isButtonEnabled: Boolean = false,
)

data class UserUIState(
    val username: String = "",
    val email: String = "",
    val address: String = "",
    val wallet: String = "",

)
