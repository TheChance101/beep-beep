package org.thechance.common.presentation.users

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.beepbeep.designSystem.ui.composable.BpCheckBox
import com.beepbeep.designSystem.ui.composable.BpChip
import com.beepbeep.designSystem.ui.composable.BpIconButton
import com.beepbeep.designSystem.ui.composable.BpOutlinedButton
import com.beepbeep.designSystem.ui.composable.BpSimpleTextField
import com.beepbeep.designSystem.ui.composable.BpTextButton
import com.beepbeep.designSystem.ui.theme.Theme
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.thechance.common.presentation.composables.BpDropdownMenu
import org.thechance.common.presentation.composables.modifier.cursorHoverIconHand
import org.thechance.common.presentation.composables.table.BpPager
import org.thechance.common.presentation.composables.table.BpTable
import org.thechance.common.presentation.composables.table.TotalItemsIndicator
import org.thechance.common.presentation.uistate.UserScreenUiState
import org.thechance.common.presentation.users.composables.UserRow


object UserScreen : Screen, KoinComponent {

    private val screenModel: UserScreenModel by inject()

    @Composable
    override fun Content() {
        val state by screenModel.state.collectAsState()
        UserContent(
            state = state
        )
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    private fun UserContent(
        state: UserScreenUiState,
    ) {
        var selectedUser by remember { mutableStateOf<String?>(null) }


        Column(
            Modifier.background(Theme.colors.surface).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Theme.dimens.space16),
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
                    text = state.search,
                    keyboardType = KeyboardType.Text,
                    trailingPainter = painterResource("ic_search.svg")
                )
                Row {
                    BpIconButton(
                        content = {
                            Text(
                                "Filter",
                                style = Theme.typography.titleMedium
                                    .copy(color = Theme.colors.contentTertiary),
                            )
                        },
                        onClick = screenModel::onClickDropDownMenu,
                        painter = painterResource("ic_filter.svg"),
                        modifier = Modifier.cursorHoverIconHand()
                    )
                    BpDropdownMenu(
                        expanded = state.isFilterDropdownMenuExpanded,
                        onDismissRequest = screenModel::onDismissDropDownMenu,
                        offset = DpOffset.Zero.copy(y = Theme.dimens.space16),
                        shape = RoundedCornerShape(Theme.radius.medium)
                            .copy(topStart = CornerSize(Theme.radius.small)),
                        modifier = Modifier.height(400.dp)
                    ) {
                        DropdownMenuItem(
                            contentPadding = PaddingValues(0.dp),
                            modifier = Modifier.width(400.dp),
                            onClick = screenModel::onDismissDropDownMenu,
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

                                    FlowRow(
                                        modifier = Modifier.fillMaxWidth()
                                            .background(Color(0xff151515))
                                            .padding(
                                                start = Theme.dimens.space24,
                                                top = Theme.dimens.space16
                                            ),
                                        horizontalArrangement = Arrangement
                                            .spacedBy(Theme.dimens.space16),
                                        maxItemsInEachRow = 3,
                                    ) {
                                        BpChip(
                                            painter = painterResource("ic_admin.xml"),
                                            onClick = {},
                                            label = "Dashboard admin",
                                            isSelected = true
                                        )
                                        BpChip(
                                            painter =
                                            painterResource("ic_restaurant_empty.svg"),
                                            onClick = {},
                                            label = "Restaurant owner",
                                            isSelected = false,
                                        )
                                        BpChip(
                                            painter =
                                            painterResource("ic_taxi_empty.svg"),
                                            onClick = {},
                                            label = "Taxi driver",
                                            isSelected = false,
                                            modifier = Modifier.padding(top = Theme.dimens.space16)
                                        )
                                        BpChip(
                                            painter =
                                            painterResource("ic_users_empty.svg"),
                                            onClick = {},
                                            label = "End user",
                                            isSelected = false,
                                            modifier = Modifier.padding(top = Theme.dimens.space16)
                                        )
                                        BpChip(
                                            painter = painterResource("ic_support.xml"),
                                            onClick = {},
                                            label = "Support",
                                            isSelected = true,
                                            modifier = Modifier.padding(top = Theme.dimens.space16)
                                        )
                                        BpChip(
                                            painter = painterResource("ic_delivery.xml"),
                                            onClick = {},
                                            label = "Delivery",
                                            isSelected = false,
                                            modifier = Modifier.padding(
                                                top = Theme.dimens.space16,
                                                bottom = Theme.dimens.space16
                                            )
                                        )
                                    }
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
                                        Modifier.fillMaxWidth().background(Theme.colors.background)
                                            .padding(
                                                horizontal = Theme.dimens.space24,
                                                vertical = Theme.dimens.space16
                                            ),
                                        verticalArrangement = Arrangement
                                            .spacedBy(Theme.dimens.space16)
                                    ) {
                                        BpCheckBox(
                                            label = "Palestine",
                                            onCheck = {},
                                            isChecked = false
                                        )
                                        BpCheckBox(
                                            label = "Iraq",
                                            onCheck = {},
                                            isChecked = false
                                        )
                                        BpCheckBox(
                                            label = "Jordan",
                                            onCheck = {},
                                            isChecked = false
                                        )
                                        BpCheckBox(
                                            label = "Egypt",
                                            onCheck = {},
                                            isChecked = false
                                        )
                                        BpCheckBox(
                                            label = "Syria",
                                            onCheck = {},
                                            isChecked = false
                                        )
                                        BpCheckBox(
                                            label = "Other",
                                            onCheck = {},
                                            isChecked = false
                                        )
                                    }
                                    Row(
                                        Modifier.padding(Theme.dimens.space24),
                                        horizontalArrangement = Arrangement
                                            .spacedBy(Theme.dimens.space16)
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

            BpTable(
                data = state.pageInfo.data,
                key = { it.username },
                headers = state.tableHeader,
                modifier = Modifier.fillMaxWidth(),
                rowContent = { user ->
                    UserRow(
                        onClickEditUser = { selectedUser = it },
                        user = user,
                        position = state.pageInfo.data.indexOf(user) + 1,
                    )
                },
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TotalItemsIndicator(
                    numberItemInPage = state.specifiedUsers,
                    totalItems = state.pageInfo.numberOfUsers,
                    itemType = "user",
                    onItemPerPageChange = { screenModel.updateSpecifiedUsers(it.toIntOrNull() ?: 10) }
                )

                BpPager(
                    maxPages = state.pageInfo.totalPages,
                    currentPage = state.currentPage ,
                    onPageClicked = screenModel::updateCurrentPage,
                )
            }
        }
    }
}