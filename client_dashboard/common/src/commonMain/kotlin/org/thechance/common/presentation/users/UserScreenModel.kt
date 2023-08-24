package org.thechance.common.presentation.users

import cafe.adriel.voyager.core.model.StateScreenModel
import kotlinx.coroutines.flow.update
import org.thechance.common.domain.usecase.IGetUsersUseCase
import org.thechance.common.presentation.uistate.UserScreenUiState
import org.thechance.common.presentation.uistate.toUiState


class UserScreenModel(
    private val getUsers: IGetUsersUseCase
) : StateScreenModel<UserScreenUiState>(UserScreenUiState()) {

    init {
        updateUsers()
    }

    fun updateCurrentPage(pageNumber: Int) {
        mutableState.update { it.copy(currentPage = pageNumber) }
        updateUsers()
    }

    fun updateSpecifiedUsers(numberOfUsers: Int) {
        mutableState.update { it.copy(specifiedUsers = numberOfUsers) }
        updateUsers()
    }

    private fun updateUsers() {
        mutableState.update {
            it.copy(pageInfo = getUsers(it.currentPage, it.specifiedUsers).toUiState())
        }
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

    fun showPermissionsDialog(user: UserScreenUiState.UserUiState) {
        mutableState.update {
            it.copy(
                permissionsDialog = it.permissionsDialog.copy(
                    show = true,
                    username = user.username,
                    permissions = user.permissions
                )
            )
        }
    }

    private fun hidePermissionsDialog() {
        mutableState.update {
            it.copy(
                permissionsDialog = it.permissionsDialog.copy(
                    show = false,
                    username = "",
                    permissions = emptyList()
                )
            )
        }
    }

    fun onPermissionDialogSave() {
        val username = mutableState.value.permissionsDialog.username
        val permissions = mutableState.value.permissionsDialog.permissions
        updateUserPermissions(username, permissions)
        hidePermissionsDialog()
    }

    fun togglePermission(permission: UserScreenUiState.PermissionUiState) {
        val permissions = getUpdatedPermissions(
            mutableState.value.permissionsDialog.permissions,
            permission
        )
        mutableState.update {
            it.copy(
                permissionsDialog = it.permissionsDialog.copy(
                    permissions = permissions
                )
            )
        }
    }

    private fun getUpdatedPermissions(
        permissions: List<UserScreenUiState.PermissionUiState>,
        permissionUiState: UserScreenUiState.PermissionUiState
    ): List<UserScreenUiState.PermissionUiState> {
        return if (permissions.contains(permissionUiState)) {
            if (permissionUiState == UserScreenUiState.PermissionUiState.END_USER) return permissions
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
                pageInfo = it.pageInfo.copy(
                    data = it.pageInfo.data.map { userUiState ->
                        if (userUiState.username == username) {
                            userUiState.copy(permissions = permissions)
                        } else {
                            userUiState
                        }
                    }
                )
            )
        }
    }
}