package org.thechance.common.ui.taxi

import cafe.adriel.voyager.core.model.StateScreenModel
import kotlinx.coroutines.flow.update
import org.thechance.common.ui.uistate.MainUiState
import org.thechance.common.ui.uistate.OverviewUiState
import org.thechance.common.ui.uistate.TaxiUiState


class TaxiScreenModel : StateScreenModel<TaxiUiState>(TaxiUiState()) {


}