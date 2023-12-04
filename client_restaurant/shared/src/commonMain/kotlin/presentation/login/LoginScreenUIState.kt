package presentation.login

import data.remote.model.RestaurantPermission
import presentation.base.ErrorState
import presentation.composables.ModalBottomSheetState

data class LoginScreenUIState(
    val userName: String = "",
    val password: String = "",
    val keepLoggedIn: Boolean = false,
    val isLoading: Boolean = false,
    val isEnable: Boolean = true,
    val isSuccess: Boolean = false,
    val error: ErrorState? = null,
    val noInternetConnection: Boolean = false,
    val isUsernameError: Boolean = false,
    val isPasswordError: Boolean = false,
    val isCredentialsError: Boolean = false,
    val usernameErrorMsg: String = "",
    val passwordErrorMsg: String = "",
    //permission
    val restaurantName: String = "",
    val showSnackbar: Boolean = false,
    val errorPermission: ErrorState? = null,
    val description: String = "",
    val ownerEmail: String = "",
    val hasPermission: Boolean = false,
    val showPermissionSheet: Boolean = false,
    val sheetState: ModalBottomSheetState = ModalBottomSheetState(),
)

enum class UserNameErrorType {
    NOTHING,
    USER_NAME_EMPTY,
    USER_NAME_WRONG
}

enum class PasswordErrorType {
    NOTHING,
    PASSWORD_EMPTY,
    PASSWORD_WRONG
}
