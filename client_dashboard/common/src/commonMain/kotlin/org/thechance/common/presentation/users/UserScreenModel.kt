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

    private fun updateUsers() {
        mutableState.update { it.copy(users = getUsers().toUiState()) }
        mutableState.update { it.copy(numberOfUsers = it.users.size) }
    }

    fun onSearchChange(text: String) {
        mutableState.update { it.copy(search = text) }
    }

    fun onClickDropDownMenu() {
        mutableState.update {
            it.copy(
                filter = UserScreenUiState.FilterUiState(
                    show = !it.filter.show,
                    permissions = it.allPermissions
                )
            )
        }
    }

    fun onDismissDropDownMenu() {
        mutableState.update {
            it.copy(
                filter = UserScreenUiState.FilterUiState(show = false)
            )
        }
    }

    fun showUserMenu(username: String) {
        mutableState.update {
            it.copy(userMenu = it.userMenu.copy(username = username))
        }
    }

    fun hideUserMenu() {
        mutableState.update {
            it.copy(userMenu = it.userMenu.copy(username = ""))
        }
    }

    fun onEditUserMenuItemClicked(user: UserScreenUiState.UserUiState) {
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

    fun onDeleteUserMenuItemClicked(user: UserScreenUiState.UserUiState) {
        println("Delete user: ${user.username}")
    }

    fun onSaveUserPermissions() {
        val username = mutableState.value.permissionsDialog.username
        val permissions = mutableState.value.permissionsDialog.permissions
        updateUserPermissions(username, permissions)
        hideUserPermissionsDialog()
    }

    fun onCancelUserPermissionsDialog() {
        hideUserPermissionsDialog()
    }

    fun onUserPermissionClicked(permission: UserScreenUiState.PermissionUiState) {
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

    private fun hideUserPermissionsDialog() {
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

    fun onItemPerPageChanged(itemPerPage: String) {
        if (itemPerPage.isNotEmpty() && itemPerPage.toInt() > 0) {
            mutableState.update { it.copy(numberItemInPage = itemPerPage.toInt()) }
        }
    }

    fun onPageClicked(page: Int) {
        mutableState.update { it.copy(selectedPage = page) }
    }

    fun onFilterPermissionClicked(permission: UserScreenUiState.PermissionUiState) {}

    fun onFilterCountryClicked(country: UserScreenUiState.CountryUiState) {
        mutableState.update { uiState ->
            uiState.copy(
                filter = uiState.filter.copy(
                    countries = uiState.filter.countries.map {
                        if (it.name == country.name) it.copy(selected = !country.selected)
                        else it
                    }
                )
            )
        }
    }
}