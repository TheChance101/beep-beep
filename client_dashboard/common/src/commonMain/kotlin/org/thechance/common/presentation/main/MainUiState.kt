package org.thechance.common.presentation.main

import org.thechance.common.presentation.util.ErrorState


data class MainUiState(
    val username: String = "",
    val firstUsernameLetter: String = "",
    val isLogin: Boolean = false,
    val error: ErrorState = ErrorState.UnKnownError,
    val isDropMenuExpanded: Boolean = false,
    val isDarkMode: Boolean = false,
    val hasInternetConnection: Boolean = true
)
