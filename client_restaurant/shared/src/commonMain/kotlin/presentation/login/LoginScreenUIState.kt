package presentation.login

import presentation.base.ErrorState
import presentation.composables.ModalBottomSheetState

data class LoginScreenUIState(
    val userName: String = "",
    val password: String = "",
    val keepLoggedIn: Boolean = false,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: ErrorState = ErrorState.InvalidCredentials,
    val usernameErrorMsg: String = "",
    val passwordErrorMsg: String = "",
    //permission
    val restaurantName: String = "",
    val description: String = "",
    val ownerEmail: String = "",
    val hasPermission: Boolean = false,
    val showPermissionSheet: Boolean = false,
    val sheetState: ModalBottomSheetState = ModalBottomSheetState()
)
