package org.thechance.common.presentation.main

import cafe.adriel.voyager.core.model.StateScreenModel
import kotlinx.coroutines.flow.update
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
            mutableState.update { it.copy(username = user.fullName) }
    }

    fun logout() {
        mutableState.update { it.copy(isLogin = false) }
    }
    fun onClickDropDownMenu(){
        mutableState.update { it.copy(isDropMenuExpanded = true) }
    }
    fun onDismissDropDownMenu(){
        mutableState.update { it.copy(isDropMenuExpanded = false) }
    }
}