package org.thechance.common.presentation.taxi

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.composable.BpIconButton
import com.beepbeep.designSystem.ui.composable.BpOutlinedButton
import com.beepbeep.designSystem.ui.composable.BpSimpleTextField
import com.beepbeep.designSystem.ui.theme.Theme
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.thechance.common.presentation.composables.modifier.noRipple
import org.thechance.common.presentation.composables.table.BpPager
import org.thechance.common.presentation.composables.table.BpTable
import org.thechance.common.presentation.composables.table.Header
import org.thechance.common.presentation.composables.table.TotalItemsIndicator
import org.thechance.common.presentation.uistate.TaxiUiState

object TaxiScreen : Screen, KoinComponent {

    private val screenModel: TaxiScreenModel by inject()

    @Composable
    override fun Content() {
        val state by screenModel.state.collectAsState()
        TaxiContent(
            state = state,
            onClickEdit = {},
            onSearchInputChange = screenModel::onSearchInputChange
        )
    }

    @OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
    @Composable
    private fun TaxiContent(
        state: TaxiUiState,
        onClickEdit: (String) -> Unit,
        onSearchInputChange: (String) -> Unit,
    ) {
        var selectedTaxi by remember { mutableStateOf<String?>(null) }
        var selectedPage by remember { mutableStateOf(1) }
        val pageCount = 5
        var numberItemInPage by remember { mutableStateOf(10) }

        val firstColumnWeight by remember { mutableStateOf(1f) }
        val otherColumnsWeight by remember { mutableStateOf(3f) }
        val headers = listOf(
            Header("No.", 1f),
            Header("Plate number", 3f),
            Header("Driver username", 3f),
            Header("Status", 3f),
            Header("Car model", 3f),
            Header("car color", 3f),
            Header("Seats", 3f),
            Header("Trips", 3f),
            Header("", 1f),
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
                    onValueChange = onSearchInputChange,
                    text = state.searchQuery,
                    keyboardType = KeyboardType.Text,
                    trailingPainter = painterResource("search.svg")
                )

                BpIconButton(
                    onClick = { /* Show Taxi Filter Dialog */ },
                    painter = painterResource("sort.svg")
                ) {
                    Text(
                        text = "Filter",
                        style = Theme.typography.titleMedium.copy(color = Theme.colors.contentTertiary),
                    )
                }
                Spacer(modifier = Modifier.weight(1f))

                BpOutlinedButton(
                    title = "Export",
                    onClick = { /* Export */ },
                )
                BpButton(
                    title = "New Taxi",
                    onClick = { /* Show New Taxi Dialog */ },
                    modifier =Modifier
                )
            }

            BpTable(
                data = state.taxis,
                key = { it.id },
                headers = headers,
                modifier = Modifier.fillMaxWidth(),
                rowsCount = pageCount,
                offset = selectedPage - 1,
                rowContent = { taxi ->
                    Text(
                        text = (state.taxis.indexOf(taxi) + 1).toString(),
                        style = Theme.typography.titleMedium.copy(color = Theme.colors.contentTertiary),
                        modifier = Modifier.weight(firstColumnWeight),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        taxi.plateNumber,
                        style = Theme.typography.titleMedium.copy(color = Theme.colors.contentPrimary),
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(otherColumnsWeight),
                        maxLines = 1,
                    )
                    Text(
                        taxi.username,
                        style = Theme.typography.titleMedium.copy(color = Theme.colors.contentPrimary),
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(otherColumnsWeight),
                        maxLines = 1,
                    )
                    Text(
                        taxi.status,
                        style = Theme.typography.titleMedium.copy(color = if (taxi.status == "Online") Theme.colors.success else if (taxi.status == "Offline") Theme.colors.primary else Theme.colors.warning),
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(otherColumnsWeight),
                        maxLines = 1,
                    )
                    Text(
                        taxi.type,
                        style = Theme.typography.titleMedium.copy(color = Theme.colors.contentPrimary),
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(otherColumnsWeight),
                        maxLines = 1,
                    )
                    Box(modifier = Modifier.weight(otherColumnsWeight)) {
                        Spacer(
                            modifier = Modifier.size(24.dp).background(
                                color = Theme.colors.primary,
                                shape = RoundedCornerShape(4.dp)
                            )
                        )
                    }

                    FlowRow(
                        modifier = Modifier.weight(otherColumnsWeight),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
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
                    Text(
                        taxi.trips,
                        style = Theme.typography.titleMedium.copy(color = Theme.colors.contentPrimary),
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(otherColumnsWeight),
                        maxLines = 1,
                    )

                    Image(
                        painter = painterResource("horizontal_dots.xml"),
                        contentDescription = null,
                        modifier = Modifier.noRipple { selectedTaxi = taxi.id }
                            .weight(firstColumnWeight),
                        colorFilter = ColorFilter.tint(color = Theme.colors.contentPrimary)
                    )
                },
            )

            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TotalItemsIndicator(
                    numberItemInPage = numberItemInPage,
                    totalItems = state.taxis.size,
                    itemType = "taxi",
                    onItemPerPageChange = { numberItemInPage = it.toIntOrNull() ?: 10 }
                )

                BpPager(
                    maxPages = pageCount,
                    currentPage = selectedPage,
                    onPageClicked = { selectedPage = it },
                )
            }
        }
    }
}