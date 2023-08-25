package org.thechance.common.presentation.users

import kotlinx.coroutines.flow.update
import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.User
import org.thechance.common.domain.usecase.IGetUsersUseCase
import org.thechance.common.presentation.base.BaseScreenModel
import org.thechance.common.presentation.util.ErrorState

class UserScreenModel(
    private val getUsers: IGetUsersUseCase
) : BaseScreenModel<UserScreenUiState, UserUiEffect>(UserScreenUiState()),
    UserScreenInteractionListener {

    init {
        getUsers()
    }

    private fun getUsers() {
        tryToExecute(
            { getUsers(state.value.currentPage, state.value.specifiedUsers) },
            ::onGetUsersSuccessfully,
            ::onError
        )
    }

    private fun onGetUsersSuccessfully(users: DataWrapper<User>) {
        updateState {
            it.copy(pageInfo = users.toUiState(), isLoading = false)
        }
    }

    private fun onError(error: ErrorState) {
        updateState { it.copy(error = error, isLoading = false) }
    }

    override fun onSearchInputChange(text: String) {
        mutableState.update { it.copy(search = text) }
    }

    override fun showFilterMenu() {
        mutableState.update {
            it.copy(
                filter = UserScreenUiState.FilterUiState(
                    show = !it.filter.show,
                    permissions = it.allPermissions
                )
            )
        }
    }

    override fun hideFilterMenu() {
        mutableState.update {
            it.copy(
                filter = it.filter.copy(show = false)
            )
        }
    }

    override fun showEditUserMenu(username: String) {
        mutableState.update {
            it.copy(userMenu = it.userMenu.copy(username = username))
        }
    }

    override fun hideEditUserMenu() {
        mutableState.update {
            it.copy(userMenu = it.userMenu.copy(username = ""))
        }
    }

    override fun onEditUserMenuItemClicked(user: UserScreenUiState.UserUiState) {
        mutableState.update {
            it.copy(
                permissionsDialog = it.permissionsDialog.copy(
                    show = true,
                    username = user.username,
                    permissions = user.permissions
                )
            )
        }.also { hideEditUserMenu() }
    }

    override fun onDeleteUserMenu(user: UserScreenUiState.UserUiState) {
        println("Delete user: ${user.username}").also { hideEditUserMenu() }
    }

    override fun onSaveEditUserMenu() {
        val username = mutableState.value.permissionsDialog.username
        val permissions = mutableState.value.permissionsDialog.permissions
        updateUserPermissions(username, permissions)
        hideUserPermissionsDialog()
    }

    override fun onCancelEditUserMenu() {
        hideUserPermissionsDialog()
    }

    override fun onEditUserMenuPermissionClick(permission: UserScreenUiState.PermissionUiState) {
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
        }.also { hideEditUserMenu() }
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

    override fun onItemsIndicatorChange(itemPerPage: Int) {
        mutableState.update { it.copy(specifiedUsers = itemPerPage) }
        getUsers()
    }

    override fun onPageClick(pageNumber: Int) {
        mutableState.update { it.copy(currentPage = pageNumber) }
        getUsers()
    }

    override fun onFilterMenuPermissionClick(permission: UserScreenUiState.PermissionUiState) {
        val permissions = getUpdatedPermissions(
            mutableState.value.filter.permissions,
            permission
        )
        mutableState.update {
            it.copy(
                filter = it.filter.copy(permissions = permissions)
            )
        }
        getUsers() // TODO: Update users with filter by permission
    }

    override fun onFilterMenuCountryClick(country: UserScreenUiState.CountryUiState) {
        mutableState.update { uiState ->
            val updatedCountries = uiState.filter.countries.map {
                if (it.name == country.name) it.copy(selected = !country.selected)
                else it
            }
            uiState.copy(filter = uiState.filter.copy(countries = updatedCountries))
        }
        getUsers() // TODO : Update users with filter by country
    }

}