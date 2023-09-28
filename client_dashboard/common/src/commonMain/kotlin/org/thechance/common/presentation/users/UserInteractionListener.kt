package org.thechance.common.presentation.users

import org.thechance.common.presentation.base.BaseInteractionListener
import org.thechance.common.presentation.overview.PermissionUiState

interface UserScreenInteractionListener : BaseInteractionListener, FilterMenuListener,
    EditUserMenuListener, PageListener {

    fun onDeleteUserMenuItemClicked(userId: String)

    fun onSearchInputChange(text: String)

    fun onRetry()

}

interface EditUserMenuListener {
    fun showEditUserMenu(username: String)

    fun hideEditUserMenu()

    fun onEditUserMenuItemClicked(user: UserScreenUiState.UserUiState)

    fun onSaveUserPermissionsDialog()

    fun onCancelUserPermissionsDialog()

    fun onUserPermissionClick(permission: UserScreenUiState.PermissionUiState)

}

interface FilterMenuListener {

    fun showFilterMenu()

    fun hideFilterMenu()

    fun onFilterMenuPermissionClick(permission: UserScreenUiState.PermissionUiState)

    fun onFilterMenuCountryClick(countryUiState: UserScreenUiState.CountryUiState)

    fun onFilterMenuSaveButtonClicked()

    fun onFilterClearAllClicked()

}

interface PageListener {
    fun onItemsIndicatorChange(itemPerPage: Int)
    fun onPageClick(pageNumber: Int)
}