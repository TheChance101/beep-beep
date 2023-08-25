package org.thechance.common.presentation.main


data class MainUiState(
    val username: String = "",
    val isLogin: Boolean = false,
    val error: String = "",
    val isDropMenuExpanded:Boolean = false
)
