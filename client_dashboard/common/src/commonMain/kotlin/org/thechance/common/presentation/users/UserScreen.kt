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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.beepbeep.designSystem.ui.composable.*
import com.beepbeep.designSystem.ui.theme.Theme
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.thechance.common.presentation.composables.BpDropdownMenu
import org.thechance.common.presentation.composables.PermissionsFlowRow
import org.thechance.common.presentation.composables.modifier.cursorHoverIconHand
import org.thechance.common.presentation.composables.modifier.noRipple
import org.thechance.common.presentation.composables.table.BpPager
import org.thechance.common.presentation.composables.table.BpTable
import org.thechance.common.presentation.composables.table.TotalItemsIndicator
import org.thechance.common.presentation.uistate.UserScreenUiState


object UserScreen : Screen, KoinComponent {

    private val screenModel: UserScreenModel by inject()

    @Composable
    override fun Content() {
        val state by screenModel.state.collectAsState()
        UserContent(
            state = state,
            onEditUserClicked = screenModel::onEditUserClicked,
            onItemPerPageChanged = screenModel::onItemPerPageChanged,
            onPageClicked = screenModel::onPageClicked,
            onFilterPermissionClicked = screenModel::onFilterPermissionClicked,
            onFilterCountryClicked = screenModel::onFilterCountryClicked,
        )
    }

    @Composable
    private fun UserContent(
        state: UserScreenUiState,
        onEditUserClicked: (UserScreenUiState.UserUiState) -> Unit,
        onItemPerPageChanged: (String) -> Unit,
        onPageClicked: (Int) -> Unit,
        onFilterPermissionClicked: (UserScreenUiState.PermissionUiState) -> Unit,
        onFilterCountryClicked: (UserScreenUiState.CountryUiState) -> Unit
    ) {
        Column(
            modifier = Modifier
                .background(Theme.colors.surface)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Theme.dimens.space16),
        ) {

            UsersFilteredSearch(
                searchText = state.search,
                allPermissions = state.allPermissions,
                selectedPermissions = state.filter.permissions,
                countries = state.filter.countries,
                onFilterPermissionClicked = onFilterPermissionClicked,
                isFilterDropdownMenuExpanded = state.filter.show,
                onFilterCountryClicked = onFilterCountryClicked,
            )

            UsersTable(
                users = state.users,
                headers = state.tableHeader,
                pageCount = state.pageCount,
                selectedPage = state.selectedPage,
                onEditUserClicked = onEditUserClicked,
            )

            UsersTableFooter(
                numberItemInPage = state.users.size,
                numberOfUsers = state.numberOfUsers,
                pageCount = state.pageCount,
                selectedPage = state.selectedPage,
                onPageClicked = onPageClicked,
                onItemPerPageChanged = onItemPerPageChanged,
            )
        }
    }

    @Composable
    private fun UsersTable(
        users: List<UserScreenUiState.UserUiState>,
        headers: List<UserScreenUiState.HeaderItem>,
        pageCount: Int,
        selectedPage: Int,
        onEditUserClicked: (UserScreenUiState.UserUiState) -> Unit,
    ) {
        BpTable(
            data = users,
            key = { it.username },
            headers = headers,
            modifier = Modifier.fillMaxWidth(),
            rowsCount = pageCount,
            offset = selectedPage - 1,
            rowContent = { user ->
                UserRow(
                    onClickEditUser = onEditUserClicked,
                    user = user,
                    position = users.indexOf(user) + 1,
                )
            },
        )
    }

    @Composable
    private fun UsersTableFooter(
        numberItemInPage: Int,
        numberOfUsers: Int,
        pageCount: Int,
        selectedPage: Int,
        onPageClicked: (Int) -> Unit,
        onItemPerPageChanged: (String) -> Unit,
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TotalItemsIndicator(
                numberItemInPage = numberItemInPage,
                totalItems = numberOfUsers,
                itemType = "user",
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
    ) {
        Row {
            BpIconButton(
                content = {
                    Text(
                        "Filter",
                        style = Theme.typography.titleMedium.copy(color = Theme.colors.contentTertiary),
                    )
                },
                onClick = screenModel::onClickDropDownMenu,
                painter = painterResource("ic_filter.svg"),
                modifier = Modifier.cursorHoverIconHand()
            )
            BpDropdownMenu(
                expanded = isFilterDropdownMenuExpanded,
                onDismissRequest = screenModel::onDismissDropDownMenu,
                offset = DpOffset.Zero.copy(y = Theme.dimens.space16),
                shape = RoundedCornerShape(Theme.radius.medium).copy(topStart = CornerSize(Theme.radius.small)),
                modifier = Modifier.height(400.dp)
            ) {
                DropdownMenuItem(
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier.width(400.dp),
                    onClick = {},
                    text = {
                        Column(Modifier.fillMaxSize()) {
                            Text(
                                "Filter",
                                style = Theme.typography.headline,
                                color = Theme.colors.contentPrimary,
                                modifier = Modifier.padding(
                                    start = Theme.dimens.space24,
                                    top = Theme.dimens.space24
                                )
                            )
                            Text(
                                "Permission",
                                style = Theme.typography.titleLarge,
                                color = Theme.colors.contentPrimary,
                                modifier = Modifier.padding(
                                    start = Theme.dimens.space24,
                                    top = Theme.dimens.space40,
                                    bottom = Theme.dimens.space16
                                ),
                            )
                            PermissionsFlowRow(
                                allPermissions = allPermissions,
                                selectedPermissions = selectedPermissions,
                                onPermissionClicked = onFilterPermissionClicked
                            )
                            Text(
                                "Country",
                                style = Theme.typography.titleLarge,
                                color = Theme.colors.contentPrimary,
                                modifier = Modifier.padding(
                                    start = Theme.dimens.space24,
                                    top = Theme.dimens.space32,
                                    bottom = Theme.dimens.space16
                                )
                            )
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Theme.colors.background)
                                    .padding(
                                        horizontal = Theme.dimens.space24,
                                        vertical = Theme.dimens.space16
                                    ),
                                verticalArrangement = Arrangement.spacedBy(Theme.dimens.space16)
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
                                Modifier.padding(Theme.dimens.space24),
                                horizontalArrangement = Arrangement.spacedBy(Theme.dimens.space16)
                            ) {
                                BpTextButton(
                                    "Cancel",
                                    onClick = screenModel::onDismissDropDownMenu,
                                    heightInDp = 40,
                                    modifier = Modifier.cursorHoverIconHand()

                                )
                                BpOutlinedButton(
                                    title = "Save",
                                    onClick = screenModel::onDismissDropDownMenu,
                                    shape = RoundedCornerShape(Theme.radius.small),
                                    modifier = Modifier.height(40.dp).weight(1f)
                                )
                            }
                        }
                    }
                )
            }
        }
    }

    @Composable
    fun UsersFilteredSearch(
        searchText: String,
        allPermissions: List<UserScreenUiState.PermissionUiState>,
        countries: List<UserScreenUiState.CountryUiState>,
        selectedPermissions: List<UserScreenUiState.PermissionUiState>,
        isFilterDropdownMenuExpanded: Boolean,
        onFilterPermissionClicked: (UserScreenUiState.PermissionUiState) -> Unit,
        onFilterCountryClicked: (UserScreenUiState.CountryUiState) -> Unit,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Theme.dimens.space16),
            verticalAlignment = Alignment.Top
        ) {
            BpSimpleTextField(
                modifier = Modifier.widthIn(max = 440.dp),
                hint = "Search for users",
                onValueChange = screenModel::onSearchChange,
                text = searchText,
                keyboardType = KeyboardType.Text,
                trailingPainter = painterResource("ic_search.svg")
            )

            UsersFilterDropdownMenu(
                allPermissions = allPermissions,
                countries = countries,
                selectedPermissions = selectedPermissions,
                isFilterDropdownMenuExpanded = isFilterDropdownMenuExpanded,
                onFilterPermissionClicked = onFilterPermissionClicked,
                onFilterCountryClicked = onFilterCountryClicked
            )
        }
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    fun RowScope.UserRow(
        onClickEditUser: (UserScreenUiState.UserUiState) -> Unit,
        position: Int,
        user: UserScreenUiState.UserUiState,
        firstColumnWeight: Float = 1f,
        otherColumnsWeight: Float = 3f,
    ) {
        Text(
            position.toString(),
            style = Theme.typography.titleMedium.copy(color = Theme.colors.contentTertiary),
            modifier = Modifier.weight(firstColumnWeight),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        Row(Modifier.weight(otherColumnsWeight), verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource("dummy_img.png"), contentDescription = null)
            Text(
                user.fullName,
                style = Theme.typography.titleMedium.copy(color = Theme.colors.contentPrimary),
                modifier = Modifier.padding(start = 16.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }

        Text(
            user.username,
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
            Modifier.weight(otherColumnsWeight),
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

        Image(
            painter = painterResource("horizontal_dots.xml"),
            contentDescription = null,
            modifier = Modifier.noRipple { onClickEditUser(user) }
                .weight(firstColumnWeight),
            colorFilter = ColorFilter.tint(color = Theme.colors.contentPrimary)
        )
    }
}