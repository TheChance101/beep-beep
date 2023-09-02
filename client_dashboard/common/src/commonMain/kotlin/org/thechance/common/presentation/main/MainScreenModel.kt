package org.thechance.common.presentation.main

import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.thechance.common.domain.usecase.ILogoutUserUseCase
import org.thechance.common.domain.usecase.IManageUsersUseCase
import org.thechance.common.domain.usecase.IThemeManagementUseCase
import org.thechance.common.presentation.base.BaseScreenModel
import org.thechance.common.presentation.util.ErrorState


class MainScreenModel(
    private val manageUsers: IManageUsersUseCase,
    private val logout: ILogoutUserUseCase,
    private val themeManagement: IThemeManagementUseCase
) : BaseScreenModel<MainUiState, MainUiEffect>(MainUiState()), MainInteractionListener {


    init {
        getUserInfo()
        getCurrentThemeMode()
    }

    private fun getUserInfo() {
        tryToExecute(
            manageUsers::getUserInfo,
            ::onGetUserInfoSuccessfully,
            ::onError
        )
    }

    private fun onGetUserInfoSuccessfully(username: String) {
        updateState {
            it.copy(
                username = username,
                firstUsernameLetter = username.first().uppercase()
            )
        }
    }

    private fun onError(error: ErrorState) {
        updateState { it.copy(error = error) }
    }

    override fun onClickDropDownMenu() {
        updateState { it.copy(isDropMenuExpanded = true) }
    }

    override fun onDismissDropDownMenu() {
        updateState { it.copy(isDropMenuExpanded = false) }
    }

    override fun onClickLogout() {
        tryToExecute(
            logout::logoutUser,
            { onLogoutSuccessfully() },
            ::onError
        )
    }

    private fun onLogoutSuccessfully() {
        updateState { it.copy(isLogin = false) }
        sendNewEffect(MainUiEffect.Logout)
    }

    override fun onSwitchTheme() {
        coroutineScope.launch(Dispatchers.IO) {
            mutableState.update { it.copy(isDarkMode = !it.isDarkMode) }
            themeManagement.switchTheme(mutableState.value.isDarkMode)
        }
    }

    private fun getCurrentThemeMode() {
        tryToCollect(
            themeManagement::getThemeMode,
            ::onGetThemeModeSuccessfully,
            ::onError
        )
    }

    private fun onGetThemeModeSuccessfully(isDarkMode: Boolean) {
        updateState { it.copy(isDarkMode = isDarkMode) }
    }

}