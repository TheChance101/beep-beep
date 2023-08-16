package org.thechance.common.ui.screen.login

import cafe.adriel.voyager.core.model.StateScreenModel
import kotlinx.coroutines.flow.update


class LoginScreenModel : StateScreenModel<LoginUiState>(LoginUiState()) {


    fun onPasswordChange(password: String) {
        mutableState.update { it.copy(password = password) }
    }
    fun onUsernameChange(username: String) {
        mutableState.update {it.copy(username = username) }
    }

}