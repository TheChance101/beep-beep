package org.thechance.common.presentation.users

import kotlinx.coroutines.Job
import org.thechance.common.domain.entity.DataWrapper
import org.thechance.common.domain.entity.User
import org.thechance.common.domain.usecase.IUsersManagementUseCase
import org.thechance.common.presentation.base.BaseScreenModel
import org.thechance.common.presentation.util.ErrorState

class UserScreenModel(
    private val userManagement: IUsersManagementUseCase,
) : BaseScreenModel<UserScreenUiState, UserUiEffect>(UserScreenUiState()),
    UserScreenInteractionListener {

    private var searchJob: Job? = null
    private var limitJob: Job? = null

    init {
        getUsers()
    }

    private fun onError(error: ErrorState) {
        when(error){
            is ErrorState.NoConnection -> {
                updateState { it.copy(hasConnection = false) }
            }
            else -> {
                updateState { it.copy(error = error, isLoading = false) }
            }
        }

    }

    override fun onRetry() {
        getUsers()
    }

    private fun getUpdatedPermissions(
        permissions: List<UserScreenUiState.PermissionUiState>,
        permissionUiState: UserScreenUiState.PermissionUiState
    ): List<UserScreenUiState.PermissionUiState> {
        return if (permissions.contains(permissionUiState)) {
            permissions.filterNot { it == permissionUiState }
        } else {
            permissions.plus(permissionUiState)
        }
    }

    // region Filter Menu
    override fun showFilterMenu() {
        updateState { it.copy(filter = it.filter.copy(show = true)) }
    }

    override fun hideFilterMenu() {
        updateState { it.copy(filter = it.filter.copy(show = false)) }
    }

    override fun onFilterMenuPermissionClick(permission: UserScreenUiState.PermissionUiState) {
        val updatedPermissions = getUpdatedPermissions(mutableState.value.filter.selectedPermissions, permission)
        updateState { it.copy(filter = it.filter.copy(selectedPermissions = updatedPermissions)) }
    }

    override fun onFilterMenuCountryClick(countryUiState: UserScreenUiState.CountryUiState) {
        val updatedCountries = mutableState.value.filter.countries.map {
            if (it.country == countryUiState.country) it.copy(isSelected = !countryUiState.isSelected) else it
        }
        updateState { it.copy(filter = it.filter.copy(countries = updatedCountries)) }
    }

    override fun onFilterMenuSaveButtonClicked() {
        hideFilterMenu()
        this.getUsers()
    }

    override fun onFilterClearAllClicked() {
        updateState {
            it.copy(
                filter = it.filter.copy(
                    selectedPermissions = emptyList(),
                    countries = it.filter.countries.map { country -> country.copy(isSelected = false) }
                )
            )
        }
    }

    // endregion

    // region Search Bar
    override fun onSearchInputChange(text: String) {
        updateState { it.copy(search = text) }
        launchSearchJob()
    }

    private fun launchSearchJob() {
        searchJob?.cancel()
        searchJob = launchDelayed(300L) { this@UserScreenModel.getUsers() }
    }

    private fun onSearchUsersSuccessfully(users: DataWrapper<User>) {
        updateState { it.copy(pageInfo = users.toUiState(), isLoading = false, hasConnection = true) }
        if (state.value.currentPage > state.value.pageInfo.totalPages) {
            onPageClick(state.value.pageInfo.totalPages)
        }
    }

    private fun getUsers() {
        tryToExecute(
            {
                userManagement.getUsers(
                    query = state.value.search.trim(),
                    byPermissions = state.value.filter.selectedPermissions.toEntity(),
                    byCountries = state.value.filter.countries.filter { it.isSelected }.map { it.country }.toCountryEntity(),
                    page = state.value.currentPage,
                    numberOfUsers = state.value.specifiedUsers
                )
            }, ::onSearchUsersSuccessfully, ::onError
        )
    }

    // endregion

    // region User Menu
    override fun showEditUserMenu(username: String) {
        updateState { it.copy(userMenu = username) }
    }

    override fun hideEditUserMenu() {
        updateState { it.copy(userMenu = "") }
    }

    override fun onEditUserMenuItemClicked(user: UserScreenUiState.UserUiState) {
        updateState {
            it.copy(
                permissionsDialog = it.permissionsDialog.copy(
                    show = true,
                    id = user.userId,
                    permissions = user.permissions
                )
            )
        }
        hideEditUserMenu()
    }

    override fun onDeleteUserMenuItemClicked(userId: String) {
        tryToExecute(
            callee = { userManagement.deleteUser(userId) },
            onSuccess = { onDeleteUserSuccess(userId) },
            onError = ::onError
        )

    }

    private fun onDeleteUserSuccess(userId: String) {
        updateState { it.copy(isLoading = false) }
        hideEditUserMenu()
        val userUiState = mutableState.value.pageInfo.data.find { it.userId == userId }
        updateState {
            it.copy(
                    isLoading = false,
                    pageInfo = it.pageInfo.copy(
                            data = it.pageInfo.data.toMutableList().apply {
                                remove(userUiState)
                            },
                    )
            )
        }
        getUsers()
    }

    // endregion

    // region Permissions Dialog
    override fun onSaveUserPermissionsDialog() {
        val userId = mutableState.value.permissionsDialog.id
        val permissions = mutableState.value.permissionsDialog.permissions
        updateUserPermissions(userId, permissions)
        hideUserPermissionsDialog()
    }

    override fun onCancelUserPermissionsDialog() {
        hideUserPermissionsDialog()
    }

    override fun onUserPermissionClick(permission: UserScreenUiState.PermissionUiState) {
        val permissions = getUpdatedPermissions(mutableState.value.permissionsDialog.permissions, permission)
        if (permission != UserScreenUiState.PermissionUiState.END_USER)
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
                    permissions = emptyList()
                )
            )
        }
    }

    private fun updateUserPermissions(
        userId: String, permissions: List<UserScreenUiState.PermissionUiState>
    ) {
        tryToExecute(
            { userManagement.updateUserPermissions(userId, permissions.toEntity()) },
            { onUpdatePermissionsSuccessfully(it.toUiState()) },
            ::onError
        )
    }

    private fun onUpdatePermissionsSuccessfully(user: UserScreenUiState.UserUiState) {
        updateState {
            it.copy(
                hasConnection = true,
                isLoading = false,
                pageInfo = it.pageInfo.copy(
                    data = it.pageInfo.data.map { userUiState ->
                        if (userUiState.userId == user.userId) userUiState.copy(permissions = user.permissions)
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
        launchLimitJob()
    }

    private fun launchLimitJob() {
        limitJob?.cancel()
        limitJob = launchDelayed(300L) { getUsers() }
    }

    override fun onPageClick(pageNumber: Int) {
        updateState { it.copy(currentPage = pageNumber) }
        getUsers()
    }
    // endregion

}