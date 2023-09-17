package org.thechance.common.presentation.login

import org.thechance.common.presentation.util.ErrorState

data class LoginUIState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: ErrorState? = null,
    val keepLoggedIn: Boolean = false,
)
