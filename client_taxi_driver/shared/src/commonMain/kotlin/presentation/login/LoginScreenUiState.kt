package presentation.login

import presentation.base.ErrorState
import presentation.composable.ModalBottomSheetState

data class LoginScreenUIState(
    val username: String = "",
    val password: String = "",
    val keepLoggedIn: Boolean = false,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: ErrorState? = null,
    val usernameErrorMsg: String = "",
    val passwordErrorMsg: String = "",
    val bottomSheetUiState: LoginScreenBottomSheetUiState = LoginScreenBottomSheetUiState()
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