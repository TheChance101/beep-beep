package org.thechance.common.presentation.uistate

import org.thechance.common.domain.entity.Admin
import org.thechance.common.domain.entity.User


data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String = "",
)


