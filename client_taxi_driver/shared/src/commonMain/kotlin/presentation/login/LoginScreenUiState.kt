package presentation.login

import presentation.base.ErrorState
import presentation.composable.ModalBottomSheetState

data class LoginScreenUIState(
    val userName: String = "",
    val password: String = "",
    val keepLoggedIn: Boolean = false,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: ErrorState? = null,
    val isUsernameError: Boolean = false,
    val isPasswordError: Boolean = false,
    val usernameErrorMsg: String = "",
    val passwordErrorMsg: String = "",
    //permission
    val driverFullName: String = "",
    val description: String = "",
    val driverEmail: String = "",
    val hasPermission: Boolean = false,
    val showPermissionSheet: Boolean = false,
    val sheetState: ModalBottomSheetState = ModalBottomSheetState(),
)