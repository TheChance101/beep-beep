package org.thechance.common.ui.users

import cafe.adriel.voyager.core.model.StateScreenModel
import kotlinx.coroutines.flow.update
import org.thechance.common.ui.uistate.MainUiState
import org.thechance.common.ui.uistate.OverviewUiState
import org.thechance.common.ui.uistate.TaxiUiState
import org.thechance.common.ui.uistate.UserUiState


class UserScreenModel : StateScreenModel<UserUiState>(UserUiState()) {


}