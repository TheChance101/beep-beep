package org.thechance.common.ui.uistate


data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String = ""
)
