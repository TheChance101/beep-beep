package org.thechance.common.presentation.taxi

import cafe.adriel.voyager.core.model.StateScreenModel
import kotlinx.coroutines.flow.update


class TaxiScreenModel : StateScreenModel<TaxiUiState>(TaxiUiState()), TaxiScreenInteractionListener {

    override fun updateAddNewTaxiDialogVisibility() {
        mutableState.update { it.copy(isAddNewTaxiDialogVisible = !mutableState.value.isAddNewTaxiDialogVisible) }
    }

}