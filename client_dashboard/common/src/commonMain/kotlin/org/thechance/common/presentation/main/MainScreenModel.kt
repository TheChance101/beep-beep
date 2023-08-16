package org.thechance.common.presentation.main

import cafe.adriel.voyager.core.model.StateScreenModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.thechance.common.domain.usecase.IGetUserInfoUseCase
import org.thechance.common.presentation.uistate.MainUiState
import org.thechance.common.presentation.uistate.toUiState


class MainScreenModel : StateScreenModel<MainUiState>(MainUiState()), KoinComponent {

    private val getUserInfo: IGetUserInfoUseCase by inject()

    init {
        getUserInfo()
    }

    private fun getUserInfo() {
            val user = getUserInfo.getUserInfo().toUiState()
            mutableState.update { it.copy(username = user.name) }
            println("User: ${user}")
    }

    fun logout() {
        mutableState.update { it.copy(isLogin = false) }
    }
}