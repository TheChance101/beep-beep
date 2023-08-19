package org.thechance.common.presentation.uistate

import org.thechance.common.domain.entity.Admin

data class AdminUiState(
    val fullName: String = ""
)

fun Admin.toUiState() = AdminUiState(
    fullName = fullName
)

