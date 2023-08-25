package org.thechance.common.presentation.main

import cafe.adriel.voyager.core.model.StateScreenModel
import kotlinx.coroutines.flow.update
import org.thechance.common.domain.usecase.IGetUserInfoUseCase


class MainScreenModel(
    private val getUserInfo: IGetUserInfoUseCase
) : StateScreenModel<MainUiState>(MainUiState()) {


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