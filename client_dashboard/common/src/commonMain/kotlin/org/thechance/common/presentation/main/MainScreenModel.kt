package org.thechance.common.presentation.main

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.thechance.common.domain.usecase.IGetUserInfoUseCase
import org.thechance.common.domain.usecase.IThemeManagementUseCase
import org.thechance.common.presentation.app.SwitchThemeInteractionListener


class MainScreenModel(
    private val getUserInfo: IGetUserInfoUseCase,
    private val themeManagement: IThemeManagementUseCase
) : StateScreenModel<MainUiState>(MainUiState()), SwitchThemeInteractionListener {


    init {
        getUserInfo()
    }

    private fun getUserInfo() {
        val user = getUserInfo.getUserInfo()
        mutableState.update { it.copy(username = user) }
    }

    fun logout() {
        mutableState.update { it.copy(isLogin = false) }
    }

    fun onClickDropDownMenu() {
        mutableState.update { it.copy(isDropMenuExpanded = true) }
    }

    fun onDismissDropDownMenu() {
        mutableState.update { it.copy(isDropMenuExpanded = false) }
    }

    override fun onSwitchTheme() {
        coroutineScope.launch(Dispatchers.IO) {
            mutableState.update { it.copy(isDarkMode = !it.isDarkMode) }
            themeManagement.switchTheme(mutableState.value.isDarkMode)
        }
    }

}