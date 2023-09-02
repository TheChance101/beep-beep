package org.thechance.common.presentation.users

import kotlinx.coroutines.Job
import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.User
import org.thechance.common.domain.usecase.ISearchUsersUseCase
import org.thechance.common.domain.usecase.IManageUsersUseCase
import org.thechance.common.presentation.base.BaseScreenModel
import org.thechance.common.presentation.util.ErrorState

class UserScreenModel(
    private val searchUsers: ISearchUsersUseCase,
    private val manageUsers: IManageUsersUseCase
) : BaseScreenModel<UserScreenUiState, UserUiEffect>(UserScreenUiState()),
    UserScreenInteractionListener {

    private var searchJob: Job? = null

    init {
        getUsers()
    }

    private fun getUsers() {
        tryToExecute(
            {
                manageUsers.getUsers(
                    byPermissions = state.value.filter.permissions.toEntity(),
                    byCountries = state.value.filter.countries.filter { it.selected }.map { it.name },
                    page = state.value.currentPage,
                    numberOfUsers = state.value.specifiedUsers
                )
            }, ::onGetUsersSuccessfully, ::onError
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

    private fun getUpdatedPermissions(
        permissions: List<UserScreenUiState.PermissionUiState>, permissionUiState: UserScreenUiState.PermissionUiState
    ): List<UserScreenUiState.PermissionUiState> {
        return if (permissions.contains(permissionUiState)) {
            permissions.filterNot { it == permissionUiState }
        } else {
            permissions.plus(permissionUiState)
        }
    }

    // region Filter Menu
    override fun showFilterMenu() {
        updateState {
            it.copy(filter = it.filter.copy(show = true))
        }
    }

    override fun hideFilterMenu() {
        updateState {
            it.copy(filter = it.filter.copy(show = false))
        }
    }

    override fun onFilterMenuPermissionClick(permission: UserScreenUiState.PermissionUiState) {
        val updatedPermissions = getUpdatedPermissions(mutableState.value.filter.permissions, permission)
        updateState { it.copy(filter = it.filter.copy(permissions = updatedPermissions)) }
    }

    override fun onFilterMenuCountryClick(country: UserScreenUiState.CountryUiState) {
        val updatedCountries = mutableState.value.filter.countries.map {
            if (it.name == country.name) it.copy(selected = !country.selected) else it
        }
        updateState { it.copy(filter = it.filter.copy(countries = updatedCountries)) }
    }

    override fun onFilterMenuSaveButtonClicked() {
        hideFilterMenu().also { if (state.value.search.isNotEmpty()) searchUsers() else getUsers() }
    }

    // endregion

    // region Search Bar
    override fun onSearchInputChange(text: String) {
        updateState { it.copy(search = text) }.also {
            searchJob?.cancel()
            searchJob = launchSearchJob()
        }
    }

    private fun launchSearchJob(): Job {
        return launchDelayed(300L) {
            if (state.value.search.isNotEmpty()) searchUsers() else getUsers()
        }
    }

    private fun onSearchUsersSuccessfully(users: DataWrapper<User>) {
        updateState { it.copy(pageInfo = users.toUiState(), isLoading = false) }
    }

    private fun searchUsers() {
        tryToExecute(
            {
                searchUsers(query = state.value.search,
                    byPermissions = state.value.filter.permissions.toEntity(),
                    byCountries = state.value.filter.countries.filter { it.selected }.map { it.name },
                    page = state.value.currentPage,
                    numberOfUsers = state.value.specifiedUsers
                )
            }, ::onSearchUsersSuccessfully, ::onError
        )
    }

    // endregion

    // region User Menu
    override fun showEditUserMenu(username: String) {
        updateState {
            it.copy(userMenu = it.userMenu.copy(username = username))
        }
    }

    override fun hideEditUserMenu() {
        updateState {
            it.copy(userMenu = it.userMenu.copy(username = ""))
        }
    }

    override fun onEditUserMenuItemClicked(user: UserScreenUiState.UserUiState) {
        updateState {
            it.copy(
                permissionsDialog = it.permissionsDialog.copy(
                    show = true,
                    username = user.username,
                    permissions = user.permissions
                )
            )
        }.also { hideEditUserMenu() }
    }

    override fun onDeleteUserMenuItemClicked(user: UserScreenUiState.UserUiState) {
        println("Delete user: ${user.username}").also { hideEditUserMenu() }
    }


    // endregion

    // region Permissions Dialog
    override fun onSaveUserPermissionsDialog() {
        val username = mutableState.value.permissionsDialog.username
        val permissions = mutableState.value.permissionsDialog.permissions
        updateUserPermissions(username, permissions)
        hideUserPermissionsDialog()
    }

    override fun onCancelUserPermissionsDialog() {
        hideUserPermissionsDialog()
    }

    override fun onUserPermissionClick(permission: UserScreenUiState.PermissionUiState) {
        val permissions = getUpdatedPermissions(mutableState.value.permissionsDialog.permissions, permission)
        updateState {
            it.copy(
                permissionsDialog = it.permissionsDialog.copy(permissions = permissions)
            )
        }
    }

    private fun hideUserPermissionsDialog() {
        updateState {
            it.copy(
                permissionsDialog = it.permissionsDialog.copy(
                    show = false,
                    username = "",
                    permissions = emptyList()
                )
            )
        }
    }

    private fun updateUserPermissions(username: String, permissions: List<UserScreenUiState.PermissionUiState>) {
        updateState {
            it.copy(
                pageInfo = it.pageInfo.copy(
                    data = it.pageInfo.data.map { userUiState ->
                        if (userUiState.username == username) userUiState.copy(permissions = permissions)
                        else userUiState
                    },
                )
            )
        }
    }

    // endregion

    // region Pagination
    override fun onItemsIndicatorChange(itemPerPage: Int) {
        updateState { it.copy(specifiedUsers = itemPerPage) }
            .also { searchUsers() }
    }

    override fun onPageClick(pageNumber: Int) {
        updateState { it.copy(currentPage = pageNumber) }
        getUsers()
    }
    // endregion
}