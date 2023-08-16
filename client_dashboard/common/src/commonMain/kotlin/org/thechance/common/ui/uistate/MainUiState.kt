package org.thechance.common.ui.uistate


data class MainUiState(
    val username: String = "",
    val isLogin: Boolean = false,
    val error: String = "",
)
