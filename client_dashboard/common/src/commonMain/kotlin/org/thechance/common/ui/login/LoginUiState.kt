package org.thechance.common.ui.screen.login


data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String = ""
)
