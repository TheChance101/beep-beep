package org.thechance.common.presentation.uistate


data class MainUiState(
    val username: String = "",
    val isLogin: Boolean = false,
    val error: String = "",
)
