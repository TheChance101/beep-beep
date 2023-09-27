package org.thechance.common.presentation.login

import org.thechance.common.presentation.restaurant.ErrorWrapper


data class LoginUIState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isUserError: ErrorWrapper? = null,
    val isPasswordError: ErrorWrapper? = null,
    val isAbleToLogin: Boolean = false,
    val snackBarTitle: String? = null,
    val isSnackBarVisible: Boolean = false
)