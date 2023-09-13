package presentation.login

import presentation.base.ErrorState
import presentation.login.composable.BottomSheetState

data class LoginScreenUIState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: ErrorState? = null,
    val userName: String = "",
    val password: String = "",
    val keepLoggedIn: Boolean = false,
    val isUsernameError: Boolean = false,
    val isPasswordError: Boolean = false,
    val usernameErrorMsg: String = "",
    val passwordErrorMsg: String = "",
    //permission
    val deliveryUsername: String = "",
    val description: String = "",
    val ownerEmail: String = "",
    val hasPermission: Boolean = false,
    val showPermissionSheet: Boolean = false,
    val sheetState: BottomSheetState = BottomSheetState(),

    )
