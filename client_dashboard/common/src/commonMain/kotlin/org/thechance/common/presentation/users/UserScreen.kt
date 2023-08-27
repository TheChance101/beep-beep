package org.thechance.common.presentation.users

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.*
import com.beepbeep.designSystem.ui.theme.Theme
import org.thechance.common.LocalDimensions
import org.thechance.common.presentation.base.BaseScreen
import org.thechance.common.presentation.composables.BpDropdownMenu
import org.thechance.common.presentation.composables.BpDropdownMenuItem
import org.thechance.common.presentation.composables.PermissionsDialog
import org.thechance.common.presentation.composables.PermissionsFlowRow
import org.thechance.common.presentation.composables.modifier.cursorHoverIconHand
import org.thechance.common.presentation.composables.modifier.noRipple
import org.thechance.common.presentation.composables.table.BpPager
import org.thechance.common.presentation.composables.table.BpTable
import org.thechance.common.presentation.composables.table.Header
import org.thechance.common.presentation.composables.table.TotalItemsIndicator
import org.thechance.common.presentation.resources.Resources

sealed interface UserUiEffect

class UserScreen :
    BaseScreen<UserScreenModel, UserUiEffect,
            UserScreenUiState, UserScreenInteractionListener>() {
    override fun onEffect(effect: UserUiEffect, navigator: Navigator) {
        TODO("Not yet implemented")
    }

    @Composable
    override fun OnRender(
        state: UserScreenUiState,
        listener: UserScreenInteractionListener
    ) {
        UserContent(
            state = state,
            pageListener = listener,
            editMenuListener = listener,
            onDeleteUserMenuItemClicked = listener::onDeleteUserMenu,
            onSearchInputChanged = listener::onSearchInputChange,
            filterMenuListener = listener
        )
    }

    @Composable
    override fun Content() {
        Init(getScreenModel())
    }

    @Composable
    private fun UserContent(
        state: UserScreenUiState,
        pageListener: PageListener,
        editMenuListener: EditUserMenuListener,
        onDeleteUserMenuItemClicked: (UserScreenUiState.UserUiState) -> Unit,
        onSearchInputChanged: (String) -> Unit,
        filterMenuListener: FilterMenuListener
    ) {
        PermissionsDialog(
            visible = state.permissionsDialog.show,
            allPermissions = state.allPermissions,
            selectedPermissions = state.permissionsDialog.permissions,
            onUserPermissionClicked = editMenuListener::onEditUserMenuPermissionClick,
            onSaveUserPermissions = editMenuListener::onSaveEditUserMenu,
            onCancelUserPermissionsDialog = editMenuListener::onCancelEditUserMenu,
        )
        Box(
            modifier = Modifier.background(Theme.colors.surface).fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(modifier = Modifier.fillMaxSize().align(Alignment.TopCenter)) {
                UsersFilteredSearch(
                    searchText = state.search,
                    allPermissions = state.allPermissions,
                    selectedPermissions = state.filter.permissions,
                    countries = state.filter.countries,
                    onFilterPermissionClicked = filterMenuListener::onFilterMenuPermissionClick,
                    isFilterDropdownMenuExpanded = state.filter.show,
                    onFilterCountryClicked = filterMenuListener::onFilterMenuCountryClick,
                    onSearchInputChanged = onSearchInputChanged,
                    onFilterMenuClicked = filterMenuListener::showFilterMenu,
                    onFilterMenuDismiss = filterMenuListener::hideFilterMenu,
                )

                UsersTable(
                    users = state.pageInfo.data,
                    headers = state.tableHeader,
                    selectedPage = state.currentPage,
                    onUserMenuClicked = editMenuListener::showEditUserMenu,
                    numberItemInPage = state.specifiedUsers,
                    numberOfUsers = state.pageInfo.numberOfUsers,
                    pageCount = state.pageInfo.totalPages,
                    onPageClicked = pageListener::onPageClick,
                    onItemPerPageChanged = pageListener::onItemsIndicatorChange,
                    editUserMenu = state.userMenu,
                    onEditUserDismiss = editMenuListener::hideEditUserMenu,
                    onEditUserMenuItemClicked = editMenuListener::onEditUserMenuItemClicked,
                    onDeleteUserMenuItemClicked = onDeleteUserMenuItemClicked,
                )
            }
        }
    }

    @Composable
    private fun ColumnScope.UsersTable(
        users: List<UserScreenUiState.UserUiState>,
        headers: List<Header>,
        selectedPage: Int,
        onUserMenuClicked: (String) -> Unit,
        numberItemInPage: Int,
        numberOfUsers: Int,
        pageCount: Int,
        onPageClicked: (Int) -> Unit,
        onItemPerPageChanged: (Int) -> Unit,
        editUserMenu: UserScreenUiState.MenuUiState,
        onEditUserDismiss: () -> Unit,
        onEditUserMenuItemClicked: (UserScreenUiState.UserUiState) -> Unit,
        onDeleteUserMenuItemClicked: (UserScreenUiState.UserUiState) -> Unit,
    ) {
        Box(modifier = Modifier.weight(1f).padding(bottom = LocalDimensions.current.space16)) {
            BpTable(
                data = users,
                key = UserScreenUiState.UserUiState::username,
                headers = headers,
                modifier = Modifier.fillMaxWidth(),
            ) { user ->
                UserRow(
                    onUserMenuClicked = onUserMenuClicked,
                    user = user,
                    position = users.indexOf(user) + 1,
                    editUserMenu = editUserMenu,
                    onEditUserDismiss = onEditUserDismiss,
                    onEditUserMenuItemClicked = onEditUserMenuItemClicked,
                    onDeleteUserMenuItemClicked = onDeleteUserMenuItemClicked,
                )
            }
        }

        UsersTableFooter(
            numberItemInPage = numberItemInPage,
            numberOfUsers = numberOfUsers,
            pageCount = pageCount,
            selectedPage = selectedPage,
            onPageClicked = onPageClicked,
            onItemPerPageChanged = onItemPerPageChanged,
        )
    }


    @Composable
    private fun UsersTableFooter(
        numberItemInPage: Int,
        numberOfUsers: Int,
        pageCount: Int,
        selectedPage: Int,
        onPageClicked: (Int) -> Unit,
        onItemPerPageChanged: (Int) -> Unit,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().background(color = Theme.colors.surface),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TotalItemsIndicator(
                numberItemInPage = numberItemInPage,
                totalItems = numberOfUsers,
                itemType = Resources.Strings.user,
                onItemPerPageChange = onItemPerPageChanged
            )
            BpPager(
                maxPages = pageCount,
                currentPage = selectedPage,
                onPageClicked = onPageClicked,
            )
        }
    }

    @Composable
    private fun UsersFilterDropdownMenu(
        isFilterDropdownMenuExpanded: Boolean,
        allPermissions: List<UserScreenUiState.PermissionUiState>,
        countries: List<UserScreenUiState.CountryUiState>,
        selectedPermissions: List<UserScreenUiState.PermissionUiState>,
        onFilterPermissionClicked: (UserScreenUiState.PermissionUiState) -> Unit,
        onFilterCountryClicked: (UserScreenUiState.CountryUiState) -> Unit,
        onFilterMenuClicked: () -> Unit,
        onFilterMenuDismiss: () -> Unit,
    ) {
        Row {
            BpIconButton(
                content = {
                    Text(
                        text = Resources.Strings.filter,
                        style = Theme.typography.titleMedium.copy(color = Theme.colors.contentTertiary),
                    )
                },
                onClick = onFilterMenuClicked,
                painter = painterResource(Resources.Drawable.filter),
                modifier = Modifier.cursorHoverIconHand()
            )
            BpDropdownMenu(
                expanded = isFilterDropdownMenuExpanded,
                onDismissRequest = onFilterMenuDismiss,
                offset = DpOffset.Zero.copy(y = LocalDimensions.current.space16),
                shape = RoundedCornerShape(Theme.radius.medium).copy(topStart = CornerSize(Theme.radius.small)),
                modifier = Modifier.height(450.dp)
            ) {
                DropdownMenuItem(
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier.width(400.dp),
                    onClick = {},
                    text = {
                        Column(Modifier.fillMaxSize()) {
                            Text(
                                text = Resources.Strings.filter,
                                style = Theme.typography.headline,
                                color = Theme.colors.contentPrimary,
                                modifier = Modifier.padding(
                                    start = LocalDimensions.current.space24,
                                    top = LocalDimensions.current.space24
                                )
                            )
                            Text(
                                text = Resources.Strings.permission,
                                style = Theme.typography.titleLarge,
                                color = Theme.colors.contentPrimary,
                                modifier = Modifier.padding(
                                    start = LocalDimensions.current.space24,
                                    top = LocalDimensions.current.space40,
                                    bottom = LocalDimensions.current.space16
                                ),
                            )
                            PermissionsFlowRow(
                                allPermissions = allPermissions,
                                selectedPermissions = selectedPermissions,
                                onUserPermissionClicked = onFilterPermissionClicked
                            )
                            Text(
                                text = Resources.Strings.country,
                                style = Theme.typography.titleLarge,
                                color = Theme.colors.contentPrimary,
                                modifier = Modifier.padding(
                                    start = LocalDimensions.current.space24,
                                    top = LocalDimensions.current.space32,
                                    bottom = LocalDimensions.current.space16
                                )
                            )
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Theme.colors.background)
                                    .padding(
                                        horizontal = LocalDimensions.current.space24,
                                        vertical = LocalDimensions.current.space16
                                    ),
                                verticalArrangement = Arrangement.spacedBy(LocalDimensions.current.space16)
                            ) {
                                countries.forEach { country ->
                                    BpCheckBox(
                                        label = country.name,
                                        onCheck = { onFilterCountryClicked(country) },
                                        isChecked = country.selected
                                    )
                                }
                            }
                            Row(
                                Modifier.padding(LocalDimensions.current.space24),
                                horizontalArrangement = Arrangement.spacedBy(LocalDimensions.current.space16),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                BpTransparentButton(
                                    title = Resources.Strings.cancel,
                                    onClick = onFilterMenuDismiss,
                                    modifier = Modifier.cursorHoverIconHand()

                                )
                                BpOutlinedButton(
                                    title = Resources.Strings.save,
                                    onClick = onFilterMenuDismiss,
                                    shape = RoundedCornerShape(Theme.radius.small),
                                    modifier = Modifier.height(50.dp).weight(1f)
                                )
                            }
                        }
                    }
                )
            }
        }
    }

    @Composable
    private fun UsersFilteredSearch(
        searchText: String,
        allPermissions: List<UserScreenUiState.PermissionUiState>,
        countries: List<UserScreenUiState.CountryUiState>,
        selectedPermissions: List<UserScreenUiState.PermissionUiState>,
        isFilterDropdownMenuExpanded: Boolean,
        onFilterPermissionClicked: (UserScreenUiState.PermissionUiState) -> Unit,
        onFilterCountryClicked: (UserScreenUiState.CountryUiState) -> Unit,
        onSearchInputChanged: (String) -> Unit,
        onFilterMenuClicked: () -> Unit,
        onFilterMenuDismiss: () -> Unit,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = LocalDimensions.current.space16),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            BpSimpleTextField(
                modifier = Modifier.widthIn(max = 440.dp),
                hint = Resources.Strings.searchForUsers,
                onValueChange = onSearchInputChanged,
                text = searchText,
                keyboardType = KeyboardType.Text,
                trailingPainter = painterResource(Resources.Drawable.search)
            )

            UsersFilterDropdownMenu(
                allPermissions = allPermissions,
                countries = countries,
                selectedPermissions = selectedPermissions,
                isFilterDropdownMenuExpanded = isFilterDropdownMenuExpanded,
                onFilterPermissionClicked = onFilterPermissionClicked,
                onFilterCountryClicked = onFilterCountryClicked,
                onFilterMenuClicked = onFilterMenuClicked,
                onFilterMenuDismiss = onFilterMenuDismiss,
            )
        }
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    private fun RowScope.UserRow(
        onUserMenuClicked: (String) -> Unit,
        position: Int,
        user: UserScreenUiState.UserUiState,
        firstColumnWeight: Float = 1f,
        otherColumnsWeight: Float = 3f,
        editUserMenu: UserScreenUiState.MenuUiState,
        onEditUserDismiss: () -> Unit,
        onEditUserMenuItemClicked: (UserScreenUiState.UserUiState) -> Unit,
        onDeleteUserMenuItemClicked: (UserScreenUiState.UserUiState) -> Unit,
    ) {
        Text(
            text = position.toString(),
            style = Theme.typography.titleMedium.copy(color = Theme.colors.contentTertiary),
            modifier = Modifier.weight(firstColumnWeight),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        Row(
            modifier = Modifier.weight(otherColumnsWeight),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(painter = painterResource(Resources.Drawable.dummyImg), contentDescription = null)
            Text(
                text = user.fullName,
                style = Theme.typography.titleMedium.copy(color = Theme.colors.contentPrimary),
                modifier = Modifier.padding(start = 16.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }

        Text(
            text = user.username,
            style = Theme.typography.titleMedium.copy(color = Theme.colors.contentPrimary),
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(otherColumnsWeight),
            maxLines = 1,
        )

        Text(
            user.email,
            style = Theme.typography.titleMedium.copy(color = Theme.colors.contentPrimary),
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(otherColumnsWeight),
            maxLines = 1,
        )

        Text(
            user.country,
            style = Theme.typography.titleMedium.copy(color = Theme.colors.contentPrimary),
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(otherColumnsWeight),
            maxLines = 1,
        )

        FlowRow(
            modifier = Modifier.weight(otherColumnsWeight),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            user.permissions.forEach {
                Icon(
                    painter = painterResource(it.iconPath),
                    contentDescription = null,
                    tint = Theme.colors.contentPrimary.copy(alpha = 0.87f),
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Box(
            modifier = Modifier.weight(firstColumnWeight),
        ) {
            Image(
                painter = painterResource(Resources.Drawable.dots),
                contentDescription = null,
                modifier = Modifier.noRipple { onUserMenuClicked(user.username) },
                colorFilter = ColorFilter.tint(color = Theme.colors.contentPrimary)
            )
            EditUserDropdownMenu(
                user = user,
                editUserMenu = editUserMenu,
                onEditUserDismiss = onEditUserDismiss,
                onEditUserMenuItemClicked = onEditUserMenuItemClicked,
                onDeleteUserMenuItemClicked = onDeleteUserMenuItemClicked,
            )
        }
    }

    @Composable
    private fun EditUserDropdownMenu(
        user: UserScreenUiState.UserUiState,
        editUserMenu: UserScreenUiState.MenuUiState,
        onEditUserDismiss: () -> Unit,
        onEditUserMenuItemClicked: (UserScreenUiState.UserUiState) -> Unit,
        onDeleteUserMenuItemClicked: (UserScreenUiState.UserUiState) -> Unit,
    ) {
        BpDropdownMenu(
            expanded = user.username == editUserMenu.username,
            onDismissRequest = onEditUserDismiss,
            shape = RoundedCornerShape(Theme.radius.medium),
            offset = DpOffset.Zero.copy(x = -LocalDimensions.current.space100)
        ) {
            Column {
                editUserMenu.items.forEach {
                    BpDropdownMenuItem(
                        onClick = {
                            when (it.text) {
                                "Edit" -> onEditUserMenuItemClicked(user)
                                "Delete" -> onDeleteUserMenuItemClicked(user)
                            }
                        },
                        text = it.text,
                        leadingIconPath = it.iconPath,
                        isSecondary = it.isSecondary,
                        showBottomDivider = it != editUserMenu.items.last()
                    )
                }
            }
        }
    }
}