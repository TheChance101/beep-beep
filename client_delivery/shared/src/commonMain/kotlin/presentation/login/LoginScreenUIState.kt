package presentation.login

import presentation.base.ErrorState

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
    val hasPermission: Boolean = false,
)
