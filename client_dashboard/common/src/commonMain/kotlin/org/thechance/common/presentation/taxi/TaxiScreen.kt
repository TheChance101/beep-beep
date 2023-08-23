package org.thechance.common.presentation.taxi

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.composable.BpIconButton
import com.beepbeep.designSystem.ui.composable.BpOutlinedButton
import com.beepbeep.designSystem.ui.composable.BpSimpleTextField
import com.beepbeep.designSystem.ui.theme.Theme
import org.thechance.common.presentation.base.BaseScreen
import org.thechance.common.presentation.composables.modifier.noRipple
import org.thechance.common.presentation.composables.table.BpPager
import org.thechance.common.presentation.composables.table.BpTable
import org.thechance.common.presentation.composables.table.TotalItemsIndicator

class TaxiScreen : BaseScreen<TaxiScreenModel, TaxiUiEffect, TaxiUiState, TaxiScreenInteractionListener>() {

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
        interactionListener: TaxiScreenInteractionListener
    ) {
        var selectedTaxi by remember { mutableStateOf<String?>(null) }
        var selectedPage by remember { mutableStateOf(1) }
        val pageCount = 2

        AddTaxiDialog(
            modifier = Modifier,
            onTaxiPlateNumberChange = interactionListener::onTaxiPlateNumberChange,
            onCancelCreateTaxiClicked = interactionListener::onCancelCreateTaxiClicked,
            isVisible = state.isAddNewTaxiDialogVisible,
            onDriverUserNamChange = interactionListener::onDriverUserNamChange,
            onCarModelChange = interactionListener::onCarModelChanged,
            onCarColorSelected = interactionListener::onCarColorSelected,
            onSeatsSelected = interactionListener::onSeatSelected,
            state = state.addNewTaxiDialogUiState,
            onCreateTaxiClicked = interactionListener::onCreateTaxiClicked
        )

        Column(
            Modifier.background(Theme.colors.surface).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Theme.dimens.space16),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.Top
            ) {
                BpSimpleTextField(
                    modifier = Modifier.widthIn(max = 440.dp),
                    hint = "Search for Taxis",
                    onValueChange = interactionListener::onSearchInputChange,
                    text = state.searchQuery,
                    keyboardType = KeyboardType.Text,
                    trailingPainter = painterResource("ic_search.svg")
                )
                BpIconButton(
                    onClick = { /* TODO: Show Taxi Filter Dialog */ },
                    painter = painterResource("ic_filter.svg"),
                ) {
                    Text(
                        text = "Filter",
                        style = Theme.typography.titleMedium.copy(color = Theme.colors.contentTertiary),
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                BpOutlinedButton(
                    title = "Export",
                    onClick = { /* TODO: Export */ },
                    textPadding = PaddingValues(horizontal = Theme.dimens.space24),
                )
                BpButton(
                    title = "New Taxi",
                    onClick = interactionListener::onAddNewTaxiClicked,
                    textPadding = PaddingValues(horizontal = Theme.dimens.space24),
                )
            }

            BpTable(
                data = state.taxis,
                key = { it.id },
                headers = state.tabHeader,
                modifier = Modifier.fillMaxWidth(),
                rowsCount = state.taxiNumberInPage.toInt(),
                offset = selectedPage - 1,
                rowContent = { taxi ->
                    TaxiRow(
                        onClickEdit = { selectedTaxi = it },
                        taxi = taxi,
                        position = state.taxis.indexOf(taxi) + 1,
                    )
                },
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TotalItemsIndicator(
                    totalItems = state.taxis.size,
                    itemType = "taxi",
                    numberItemInPage = state.taxiNumberInPage,
                    onItemPerPageChange = interactionListener::onTaxiNumberChange
                )
                BpPager(
                    maxPages = pageCount,
                    currentPage = selectedPage,
                    onPageClicked = { selectedPage = it },
                )
            }
        }
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    private fun RowScope.TaxiRow(
        onClickEdit: (id: String) -> Unit,
        position: Int,
        taxi: TaxiDetailsUiState,
        firstColumnWeight: Float = 1f,
        otherColumnsWeight: Float = 3f,
    ) {

        TitleField(
            text = position.toString(),
            modifier = Modifier.weight(otherColumnsWeight),
            color = Theme.colors.contentTertiary
        )
        TitleField(text = taxi.plateNumber, modifier = Modifier.weight(otherColumnsWeight))
        TitleField(text = taxi.username, modifier = Modifier.weight(otherColumnsWeight))
        TitleField(
            text = taxi.statusText,
            modifier = Modifier.weight(otherColumnsWeight),
            color = taxi.statusColor
        )
        TitleField(text = taxi.type, modifier = Modifier.weight(otherColumnsWeight))
        SquareColorField(modifier = Modifier.weight(otherColumnsWeight), color = Color(taxi.color.hexadecimal))
        FlowRow(
            modifier = Modifier.weight(otherColumnsWeight),
            horizontalArrangement = Arrangement.spacedBy(Theme.dimens.space8),
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
        TitleField(text = taxi.trips, modifier = Modifier.weight(otherColumnsWeight))
        Image(
            painter = painterResource("horizontal_dots.xml"),
            contentDescription = null,
            modifier = Modifier.noRipple { onClickEdit(taxi.id) }
                .weight(firstColumnWeight),
            colorFilter = ColorFilter.tint(color = Theme.colors.contentPrimary)
        )
    }

    @Composable
   private fun TitleField(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = Theme.colors.contentPrimary,
    ) {
        Text(
            text = text,
            style = Theme.typography.titleMedium,
            overflow = TextOverflow.Ellipsis,
            modifier = modifier,
            maxLines = 1,
            color = color
        )
    }

    @Composable
    private fun SquareColorField(modifier: Modifier = Modifier, color: Color) {
        Box(modifier = modifier) {
            Spacer(
                modifier = Modifier.size(Theme.dimens.space24)
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
}
