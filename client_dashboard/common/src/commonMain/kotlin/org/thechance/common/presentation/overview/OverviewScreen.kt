package org.thechance.common.presentation.overview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.beepbeep.designSystem.ui.composable.BpButton
import com.beepbeep.designSystem.ui.theme.Theme
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.thechance.common.presentation.uistate.OverviewUiState

object OverviewScreen : Screen, KoinComponent {

    private val screenModel: OverviewScreenModel by inject()

    @Composable
    override fun Content() {
        val navigate = LocalNavigator.currentOrThrow
        val state by screenModel.state.collectAsState()

        OverviewContent(
            state = state,
            onClickBack = { navigate.popUntilRoot() }
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OverviewContent(
    state: OverviewUiState,
    onClickBack: () -> Unit,
) {

    Column(
        Modifier.background(Theme.colors.surface).fillMaxSize()
    ) {
        Box(Modifier.weight(1f)) {
            Text(text = "Main Screen", color = Theme.colors.onPrimary)

        }
    }
}