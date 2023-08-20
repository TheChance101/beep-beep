package org.thechance.common.presentation.taxi

import cafe.adriel.voyager.core.model.StateScreenModel
import kotlinx.coroutines.flow.update


class TaxiScreenModel : StateScreenModel<TaxiUiState>(TaxiUiState()), TaxiScreenInteractionListener {

    override fun updateAddNewTaxiDialogVisibility() {
        val taxiDialog = mutableState.value.addTaxiDialogUiState
        val visibilityState = !taxiDialog.isAddNewTaxiDialogVisible
        mutableState.update { it.copy(addTaxiDialogUiState = taxiDialog.copy(isAddNewTaxiDialogVisible = visibilityState)) }
    }

    override fun onTaxiPlateNumberChange(number: String) {
        mutableState.update { it.copy(addTaxiDialogUiState = it.addTaxiDialogUiState.copy(taxiPlateNumber = number)) }
    }

    override fun onDriverUserNamChange(name: String) {
        mutableState.update { it.copy(addTaxiDialogUiState = it.addTaxiDialogUiState.copy(driverUserName = name)) }

    }

    override fun onCarModelChange(model: String) {
        mutableState.update { it.copy(addTaxiDialogUiState = it.addTaxiDialogUiState.copy(carModel = model)) }

    }

    override fun onCarColorSelected(color: CarColor) {
        mutableState.update { it.copy(addTaxiDialogUiState = it.addTaxiDialogUiState.copy(selectedCarColor = color)) }

    }

    override fun onSeatsSelected(seats: Int) {
        mutableState.update { it.copy(addTaxiDialogUiState = it.addTaxiDialogUiState.copy(seats = seats)) }
    }

}