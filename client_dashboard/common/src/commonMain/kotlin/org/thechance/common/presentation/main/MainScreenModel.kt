package org.thechance.common.presentation.main

import org.thechance.common.domain.usecase.IManageUsersUseCase
import org.thechance.common.presentation.base.BaseScreenModel
import org.thechance.common.presentation.util.ErrorState


class MainScreenModel(
    private val manageUsers: IManageUsersUseCase
) : BaseScreenModel<MainUiState, MainUiEffect>(MainUiState()), MainInteractionListener {


    init {
        getUserInfo()
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
        updateState { it.copy(isLogin = false) }
        sendNewEffect(MainUiEffect.Logout)
    }
}