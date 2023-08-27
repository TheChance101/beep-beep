package org.thechance.common.presentation.taxi

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.composable.BpChip
import com.beepbeep.designSystem.ui.composable.BpIconButton
import com.beepbeep.designSystem.ui.composable.BpOutlinedButton
import com.beepbeep.designSystem.ui.composable.BpSimpleTextField
import com.beepbeep.designSystem.ui.composable.BpTransparentButton
import com.beepbeep.designSystem.ui.theme.Theme
import kotlinx.coroutines.delay
import org.thechance.common.LocalDimensions
import org.thechance.common.domain.entity.CarColor
import org.thechance.common.domain.util.TaxiStatus
import org.thechance.common.presentation.base.BaseScreen
import org.thechance.common.presentation.composables.BpDropdownMenu
import org.thechance.common.presentation.composables.BpDropdownMenuItem
import org.thechance.common.presentation.composables.CarColors
import org.thechance.common.presentation.composables.SeatsBar
import org.thechance.common.presentation.composables.SnackBar
import org.thechance.common.presentation.composables.modifier.cursorHoverIconHand
import org.thechance.common.presentation.composables.modifier.noRipple
import org.thechance.common.presentation.composables.table.BpPager
import org.thechance.common.presentation.composables.table.BpTable
import org.thechance.common.presentation.composables.table.TotalItemsIndicator

class TaxiScreen :
    BaseScreen<TaxiScreenModel, TaxiUiEffect, TaxiUiState, TaxiInteractionListener>() {

    @Composable
    override fun Content() {
        Init(getScreenModel())
    }

    override fun onEffect(effect: TaxiUiEffect, navigator: Navigator) {
        when (effect) {
            //TODO: effects
            else -> {}
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun OnRender(
        state: TaxiUiState,
        listener: TaxiInteractionListener
    ) {

        AddTaxiDialog(
            modifier = Modifier,
            onTaxiPlateNumberChange = listener::onTaxiPlateNumberChange,
            onCancelCreateTaxiClicked = listener::onCancelCreateTaxiClicked,
            isVisible = state.isAddNewTaxiDialogVisible,
            onDriverUserNamChange = listener::onDriverUserNamChange,
            onCarModelChange = listener::onCarModelChanged,
            onCarColorSelected = listener::onCarColorSelected,
            onSeatsSelected = listener::onSeatSelected,
            state = state.newTaxiInfo,
            onCreateTaxiClicked = listener::onCreateTaxiClicked
        )

        Box(
            modifier = Modifier.background(Theme.colors.surface).fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {

            Column(modifier = Modifier.fillMaxSize().align(Alignment.TopCenter)) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(bottom = LocalDimensions.current.space16),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    BpSimpleTextField(
                        modifier = Modifier.widthIn(max = 440.dp),
                        hint = "Search for Taxis",
                        onValueChange = listener::onSearchInputChange,
                        text = state.searchQuery,
                        keyboardType = KeyboardType.Text,
                        trailingPainter = painterResource("ic_search.svg")
                    )

                    Column {
                        BpIconButton(
                            onClick = listener::onFilterMenuClicked,
                            painter = painterResource("ic_filter.svg"),
                            modifier = Modifier.cursorHoverIconHand()
                        ) {
                            Text(
                                text = "Filter",
                                style = Theme.typography.titleMedium.copy(color = Theme.colors.contentTertiary),
                            )
                        }
                        TaxiFilterDropdownMenu(
                            onFilterMenuDismiss = listener::onFilterMenuDismiss,
                            isFilterDropdownMenuExpanded = state.isFilterDropdownMenuExpanded,
                            taxi = state.taxiFilterUiState,
                            onCarColorSelected = listener::onSelectedCarColor,
                            onSeatsSelected = listener::onSelectedSeat,
                            onStatusSelected = listener::onSelectedStatus,
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    BpOutlinedButton(
                        title = "Export",
                        onClick = listener::onExportReportClicked,
                        textPadding = PaddingValues(horizontal = LocalDimensions.current.space24),
                        modifier = Modifier.cursorHoverIconHand()
                    )
                    BpButton(
                        title = "New Taxi",
                        onClick = listener::onAddNewTaxiClicked,
                        textPadding = PaddingValues(horizontal = LocalDimensions.current.space24),
                        modifier = Modifier.cursorHoverIconHand()
                    )
                }
                BpTable(
                    data = state.pageInfo.data,
                    key = { it.username },
                    headers = state.tabHeader,
                    modifier = Modifier.fillMaxWidth(),
                    rowContent = { taxi ->
                        TaxiRow(
                            taxi = taxi,
                            position = state.pageInfo.data.indexOf(taxi) + 1,
                            onDropdownMenuClicked = listener::showTaxiMenu,
                            onDropdownMenuDismiss = listener::hideTaxiMenu,
                            onEditTaxiClicked = listener::onEditTaxiClicked,
                            onDeleteTaxiClicked = listener::onDeleteTaxiClicked,
                            editTaxiMenu = state.taxiMenu,
                        )
                    },
                )
                TaxisTableFooter(
                    selectedPage = state.currentPage,
                    numberItemInPage = state.specifiedTaxis,
                    numberOfTaxis = state.pageInfo.numberOfTaxis,
                    pageCount = state.pageInfo.totalPages,
                    onPageClicked = listener::onPageClick,
                    onItemPerPageChanged = listener::onItemsIndicatorChange,
                )
            }

            AnimatedVisibility(
                visible = state.isExportReportSuccessfully,
                enter = fadeIn(initialAlpha = 0.3f),
                exit = fadeOut(targetAlpha = 0.3f)
            ) {
                SnackBar(
                    modifier = Modifier.zIndex(3f).align(Alignment.BottomCenter),
                    onDismiss = listener::onDismissExportReportSnackBar
                ) {
                    Image(
                        painter = painterResource("ic_download_mark.svg"),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(color = Theme.colors.success),
                        modifier = Modifier.padding(LocalDimensions.current.space16)
                    )
                    Text(
                        text = "Your file download was successful.",
                        style = Theme.typography.titleMedium,
                        color = Theme.colors.success,
                    )
                }
            }

            LaunchedEffect(state.isExportReportSuccessfully) {
                if (state.isExportReportSuccessfully) {
                    delay(2000)
                    listener.onDismissExportReportSnackBar()
                }
            }
        }
    }
    //region components

    @Composable
    private fun TaxiFilterDropdownMenu(
        isFilterDropdownMenuExpanded: Boolean = false,
        onFilterMenuDismiss: () -> Unit = {},
        taxi: TaxiFilterUiState,
        onSeatsSelected: (Int) -> Unit,
        onCarColorSelected: (CarColor) -> Unit,
        onStatusSelected: (TaxiStatus) -> Unit,
    ) {
        BpDropdownMenu(
            expanded = isFilterDropdownMenuExpanded,
            onDismissRequest = onFilterMenuDismiss,
            offset = DpOffset.Zero.copy(y = LocalDimensions.current.space16),
            shape = RoundedCornerShape(Theme.radius.medium).copy(topStart = CornerSize(Theme.radius.small)),
            modifier = Modifier.width(400.dp),
        ) {
            DropdownMenuItem(
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.width(500.dp),
                onClick = {},
                text = {
                    Column(Modifier.fillMaxSize()) {
                        Text(
                            "Filter",
                            style = Theme.typography.headline,
                            color = Theme.colors.contentPrimary,
                            modifier = Modifier.padding(LocalDimensions.current.space24)
                        )
                        Text(
                            "Status",
                            style = Theme.typography.titleLarge,
                            color = Theme.colors.contentPrimary,
                            modifier = Modifier
                                .padding(
                                    horizontal = LocalDimensions.current.space24,
                                    vertical = LocalDimensions.current.space16
                                ),
                        )
                        CarStatus(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = Theme.colors.background)
                                .padding(LocalDimensions.current.space24),
                            status = TaxiStatus.values().toList(),
                            onSelectState = onStatusSelected,
                            selectedStatus = taxi.status,
                        )

                        Text(
                            "Car Color",
                            style = Theme.typography.titleLarge,
                            color = Theme.colors.contentPrimary,
                            modifier = Modifier.padding(
                                horizontal = LocalDimensions.current.space24,
                                vertical = LocalDimensions.current.space16
                            ),
                        )
                        CarColors(
                            modifier = Modifier.fillMaxWidth()
                                .background(color = Theme.colors.background).padding(
                                    horizontal = LocalDimensions.current.space24,
                                    vertical = LocalDimensions.current.space16
                                ),
                            colors = CarColor.values().toList(),
                            onSelectColor = onCarColorSelected,
                            selectedCarColor = taxi.carColor
                        )
                        Text(
                            "Seats",
                            style = Theme.typography.titleLarge,
                            color = Theme.colors.contentPrimary,
                            modifier = Modifier.padding(
                                horizontal = LocalDimensions.current.space24,
                                vertical = LocalDimensions.current.space16
                            )
                        )
                        SeatsBar(
                            selectedSeatsCount = taxi.seats,
                            count = 6,
                            selectedIcon = painterResource(
                                if (isSystemInDarkTheme()) "ic_filled_seat_dark.svg" else "ic_filled_seat_light.svg"
                            ),
                            notSelectedIcon = painterResource(
                                if (isSystemInDarkTheme()) "ic_outlined_seat_dark.svg" else "ic_outlined_seat_light.svg"
                            ),
                            iconsSize = LocalDimensions.current.space24,
                            iconsPadding = PaddingValues(horizontal = LocalDimensions.current.space8),
                            modifier = Modifier.fillMaxWidth()
                                .background(color = Theme.colors.background).padding(
                                    horizontal = LocalDimensions.current.space24,
                                    vertical = LocalDimensions.current.space16
                                ),
                            onClick = onSeatsSelected
                        )
                        Row(
                            Modifier.padding(LocalDimensions.current.space24),
                            horizontalArrangement = Arrangement.spacedBy(LocalDimensions.current.space16),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            BpTransparentButton(
                                "Cancel",
                                onClick = onFilterMenuDismiss,
                                modifier = Modifier.cursorHoverIconHand()

                            )
                            BpOutlinedButton(
                                title = "Save",
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

    @Composable
    private fun TaxisTableFooter(
        numberItemInPage: Int,
        numberOfTaxis: Int,
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
                totalItems = numberOfTaxis,
                itemType = "taxi",
                onItemPerPageChange = onItemPerPageChanged
            )
            BpPager(
                maxPages = pageCount,
                currentPage = selectedPage,
                onPageClicked = onPageClicked,
            )
        }
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    private fun RowScope.TaxiRow(
        position: Int,
        taxi: TaxiDetailsUiState,
        firstColumnWeight: Float = 1f,
        otherColumnsWeight: Float = 3f,
        onDropdownMenuClicked: (String) -> Unit,
        editTaxiMenu: MenuUiState,
        onDropdownMenuDismiss: () -> Unit,
        onEditTaxiClicked: (TaxiDetailsUiState) -> Unit,
        onDeleteTaxiClicked: (TaxiDetailsUiState) -> Unit,
    ) {
        TitleField(
            text = position.toString(),
            color = Theme.colors.contentTertiary,
            weight = firstColumnWeight
        )
        TitleField(text = taxi.plateNumber)
        TitleField(text = taxi.username)
        TitleField(text = taxi.status.getStatusName(), color = taxi.statusColor)
        TitleField(text = taxi.type)
        SquareColorField(
            modifier = Modifier.weight(otherColumnsWeight),
            color = Color(taxi.color.hexadecimal)
        )
        FlowRow(
            modifier = Modifier.weight(otherColumnsWeight),
            horizontalArrangement = Arrangement.spacedBy(LocalDimensions.current.space8),
            maxItemsInEachRow = 3,
        ) {
            repeat(taxi.seats) {
                Icon(
                    painter = painterResource("outline_seat.xml"),
                    contentDescription = null,
                    tint = Theme.colors.contentPrimary.copy(alpha = 0.87f),
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        TitleField(text = taxi.trips)
        Box(modifier = Modifier.weight(firstColumnWeight)) {
            Image(
                painter = painterResource("horizontal_dots.xml"),
                contentDescription = null,
                modifier = Modifier.noRipple { onDropdownMenuClicked(taxi.username) },
                colorFilter = ColorFilter.tint(color = Theme.colors.contentPrimary)
            )
            EditTaxiDropdownMenu(
                taxi = taxi,
                editTaxiMenu = editTaxiMenu,
                onDropdownMenuDismiss = onDropdownMenuDismiss,
                onEditTaxiClicked = onEditTaxiClicked,
                onDeleteTaxiClicked = onDeleteTaxiClicked,
            )
        }
    }

    @Composable
    private fun EditTaxiDropdownMenu(
        taxi: TaxiDetailsUiState,
        onDropdownMenuDismiss: () -> Unit,
        editTaxiMenu: MenuUiState,
        onEditTaxiClicked: (TaxiDetailsUiState) -> Unit,
        onDeleteTaxiClicked: (TaxiDetailsUiState) -> Unit,
    ) {
        BpDropdownMenu(
            expanded = taxi.username == editTaxiMenu.username,
            onDismissRequest = onDropdownMenuDismiss,
            shape = RoundedCornerShape(Theme.radius.medium),
            offset = DpOffset.Zero.copy(x = -LocalDimensions.current.space100)
        ) {
            Column {
                editTaxiMenu.items.forEach {
                    BpDropdownMenuItem(
                        onClick = {
                            when (it.text) {
                                "Edit" -> onEditTaxiClicked(taxi)
                                "Delete" -> onDeleteTaxiClicked(taxi)
                            }
                        },
                        text = it.text,
                        leadingIconPath = it.iconPath,
                        isSecondary = it.isSecondary,
                        showBottomDivider = it != editTaxiMenu.items.last()
                    )
                }
            }
        }
    }

    @Composable
    private fun RowScope.TitleField(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = Theme.colors.contentPrimary,
        weight: Float = 3f
    ) {
        Text(
            text = text,
            style = Theme.typography.titleMedium,
            overflow = TextOverflow.Ellipsis,
            modifier = modifier.weight(weight),
            maxLines = 1,
            color = color
        )
    }

    @Composable
    private fun SquareColorField(modifier: Modifier = Modifier, color: Color) {
        Box(modifier = modifier) {
            Spacer(
                modifier = Modifier.size(LocalDimensions.current.space24)
                    .background(
                        color = color,
                        shape = RoundedCornerShape(Theme.radius.small),
                    )
                    .border(
                        width = 1.dp,
                        color = Theme.colors.contentBorder,
                        shape = RoundedCornerShape(Theme.radius.small),
                    )
            )
        }
    }

    @Composable
    fun CarStatus(
        modifier: Modifier = Modifier,
        status: List<TaxiStatus>,
        onSelectState: (TaxiStatus) -> Unit,
        selectedStatus: TaxiStatus,
    ) {

        Row(
            modifier = modifier,
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(LocalDimensions.current.space16)
        ) {
            status.forEach { status ->
                BpChip(
                    modifier = Modifier.height(LocalDimensions.current.space32),
                    label = status.getStatusName(),
                    onClick = { onSelectState(status) },
                    isSelected = selectedStatus == status,
                )
            }
        }
    }
    //endregion
}
