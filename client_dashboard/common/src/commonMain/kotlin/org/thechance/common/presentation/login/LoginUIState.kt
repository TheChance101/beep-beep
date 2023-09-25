package org.thechance.common.presentation.login

import org.thechance.common.presentation.restaurant.ErrorWrapper
import org.thechance.common.presentation.util.ErrorState


data class LoginUIState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: ErrorState? = null,
    val isUserError: ErrorWrapper? = null,
    val isPasswordError: ErrorWrapper? = null,
    val isAbleToLogin: Boolean = false,
    val hasInternetConnection: Boolean = true
)