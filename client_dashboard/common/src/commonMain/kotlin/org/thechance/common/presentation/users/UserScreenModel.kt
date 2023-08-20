package org.thechance.common.presentation.users

import cafe.adriel.voyager.core.model.StateScreenModel
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.thechance.common.domain.usecase.IGetUsersUseCase
import org.thechance.common.presentation.uistate.UserScreenUiState
import org.thechance.common.presentation.uistate.toUiState


class UserScreenModel : StateScreenModel<UserScreenUiState>(UserScreenUiState()), KoinComponent {
    private val getUsers: IGetUsersUseCase by inject()

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

    fun togglePermission(
        username: String,
        permissionUiState: UserScreenUiState.PermissionUiState
    ) {
        val user = findUserByUsername(username) ?: return
        val permissions = getUpdatedPermissions(user.permissions, permissionUiState)
        updateUserPermissions(username, permissions)
    }

    private fun findUserByUsername(username: String): UserScreenUiState.UserUiState? {
        return mutableState.value.users.find { it.username == username }
    }

    private fun getUpdatedPermissions(
        permissions: List<UserScreenUiState.PermissionUiState>,
        permissionUiState: UserScreenUiState.PermissionUiState
    ): List<UserScreenUiState.PermissionUiState> {
        return if (permissions.contains(permissionUiState)) {
            if (permissions.size == 1) return permissions
            permissions.filterNot { it == permissionUiState }
        } else {
            permissions.plus(permissionUiState)
        }
    }

    private fun updateUserPermissions(
        username: String,
        permissions: List<UserScreenUiState.PermissionUiState>
    ) {
        mutableState.update {
            it.copy(
                users = it.users.map { userUiState ->
                    if (userUiState.username == username) {
                        userUiState.copy(permissions = permissions)
                    } else {
                        userUiState
                    }
                }
            )
        }
    }
}