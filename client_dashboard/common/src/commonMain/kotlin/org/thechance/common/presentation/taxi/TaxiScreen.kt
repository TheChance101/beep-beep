package org.thechance.common.presentation.taxi

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import com.beepbeep.designSystem.ui.composable.BpTextButton
import com.beepbeep.designSystem.ui.theme.Theme
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.thechance.common.presentation.uistate.TaxiUiState

object TaxiScreen : Screen, KoinComponent {

    private val screenModel: TaxiScreenModel by inject()

    @Composable
    override fun Content() {
        val state by screenModel.state.collectAsState()
        TaxiContent(state = state , screenModel::updateAddNewTaxiDialogVisibility)
    }

    @Composable
    private fun TaxiContent(
        state: TaxiUiState,
        updateAddNewTaxiDialogVisibility: () -> Unit
    ) {

        Column(
            Modifier.background(Theme.colors.surface).fillMaxSize()
        ) {
            Box(Modifier.weight(1f)) {
                Text(text = "Taxi Screen", color = Theme.colors.onPrimary)

                BpTextButton(
                    text = "Add Taxi",
                    updateAddNewTaxiDialogVisibility,
                    modifier = Modifier,
                )

                AddNewTaxiDialog(
                    modifier = Modifier,
                    onTaxiPlateNumberChange = {},
                    setShowDialog = updateAddNewTaxiDialogVisibility,
                    state.isAddNewTaxiDialogVisible
                )
            }
        }
    }
}