package org.thechance.common.presentation.users

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.composable.BpIconButton
import com.beepbeep.designSystem.ui.composable.BpOutlinedButton
import com.beepbeep.designSystem.ui.composable.BpSimpleTextField
import com.beepbeep.designSystem.ui.composable.BpTextField
import com.beepbeep.designSystem.ui.composable.BpToggleButton
import com.beepbeep.designSystem.ui.theme.Theme
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.thechance.common.presentation.composables.table.BpPager
import org.thechance.common.presentation.composables.table.BpTable
import org.thechance.common.presentation.composables.table.Header
import org.thechance.common.presentation.composables.table.TotalItemsIndicator
import org.thechance.common.presentation.composables.table.UserRow
import org.thechance.common.presentation.composables.table.getDummyUsers
import org.thechance.common.presentation.uistate.UserUiState


object UserScreen : Screen, KoinComponent {

    private val screenModel: UserScreenModel by inject()

    @Composable
    override fun Content() {
        val state by screenModel.state.collectAsState()

        UserContent(state = state)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun UserContent(
        state: UserUiState,
    ) {
        //This need to change to get it from state
        var selectedUser by remember { mutableStateOf<String?>(null) }
        var selectedPage by remember { mutableStateOf(1) }
        var numberItemInPage by remember { mutableStateOf(10) }
        var search by remember { mutableStateOf("") }
        val pageCount = 2
        ////////

        Column(
            Modifier.background(Theme.colors.surface).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Theme.dimens.space16),
        ) {

            val headers = listOf(
                Header("No.", 1f),
                Header("Users", 3f),
                Header("Username", 3f),
                Header("Email", 3f),
                Header("Country", 3f),
                Header("Permission", 3f),
                Header("", 1f),
            )
            val users = getDummyUsers()

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.Top
            ) {
                BpSimpleTextField(
                    modifier = Modifier.widthIn(max = 440.dp),
                    hint = "Search for users",
                    onValueChange = { search = it },
                    text = search,
                    keyboardType = KeyboardType.Text,
                    trailingPainter = painterResource("search.svg")
                )

                BpIconButton(
                    content = {
                        Text(
                            "Filter",
                            style = Theme.typography.titleMedium.copy(color = Theme.colors.contentTertiary),
                        )
                    },
                    onClick = {},
                    painter = painterResource("sort.svg")
                )

            }

            BpTable(
                data = users,
                key = { it.id },
                headers = headers,
                modifier = Modifier.fillMaxWidth(),
                rowsCount = pageCount,
                offset = selectedPage - 1,
                rowContent = { user ->
                    UserRow(
                        onClickEditUser = { selectedUser = it },
                        user = user,
                        position = users.indexOf(user) + 1,
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
                    totalItems = 190,
                    itemType = "user",
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