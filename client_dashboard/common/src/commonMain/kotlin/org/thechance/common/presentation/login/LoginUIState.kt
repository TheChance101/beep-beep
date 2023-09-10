package org.thechance.common.presentation.login

import org.thechance.common.presentation.restaurant.ErrorWrapper
import org.thechance.common.presentation.util.ErrorState


data class LoginUIState(
    val username: String = "",
    val usernameError: ErrorWrapper = ErrorWrapper(),
    val password: String = "",
    val passwordError: ErrorWrapper = ErrorWrapper(),
    val isLoading: Boolean = false,
    val error: ErrorState? = null,
    val keepLoggedIn: Boolean = false,
)