package org.thechance.common.presentation.users

import cafe.adriel.voyager.core.model.StateScreenModel
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.thechance.common.domain.usecase.IGetUsersUseCase
import org.thechance.common.presentation.uistate.UserScreenUiState
import org.thechance.common.presentation.uistate.toUiState


class UserScreenModel(
    private val getUsers: IGetUsersUseCase
) : StateScreenModel<UserScreenUiState>(UserScreenUiState()) {

    init {
        updateUsers()
    }

    private fun updateUsers() {
        mutableState.update { it.copy(users = getUsers().toUiState()) }
        mutableState.update { it.copy(numberOfUsers = it.users.size) }
    }

    fun onSearchChange(text: String) {
        mutableState.update { it.copy(search = text) }
    }

    fun onClickDropDownMenu() {
        mutableState.update { it.copy(isFilterDropdownMenuExpanded = true) }
    }

    fun onDismissDropDownMenu() {
        mutableState.update { it.copy(isFilterDropdownMenuExpanded = false) }
    }
}