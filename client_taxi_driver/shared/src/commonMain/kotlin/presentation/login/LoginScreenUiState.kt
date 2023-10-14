package presentation.login

import presentation.base.ErrorState
import presentation.composable.ModalBottomSheetState

data class LoginScreenUIState(
    val username: String = "",
    val password: String = "",
    val keepLoggedIn: Boolean = false,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isUsernameError: Boolean = false,
    val isPasswordError: Boolean = false,
    val isEnable: Boolean = true,
    val error: ErrorState? = null,
    val usernameErrorMsg: String = "",
    val passwordErrorMsg: String = "",
    val snackBarMessage: String = "",
    val showSnackBar: Boolean = false,
    val bottomSheetUiState: LoginScreenBottomSheetUiState = LoginScreenBottomSheetUiState(),
)

data class LoginScreenBottomSheetUiState(
    val driverFullName: String = "",
    val description: String = "",
    val driverEmail: String = "",
    val hasPermission: Boolean = false,
    val sheetState: ModalBottomSheetState = ModalBottomSheetState(),
    val showPermissionSheet: Boolean = false,
    val driverFullNameErrorMsg: String = "",
    val driverEmailErrorMsg: String = "",
)