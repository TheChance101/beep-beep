package org.thechance.common.presentation.taxi

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.thechance.common.domain.entity.CarColor
import org.thechance.common.domain.usecase.ICreateNewTaxiUseCase
import org.thechance.common.domain.usecase.IGetTaxisUseCase


class TaxiScreenModel(
    private val getTaxis: IGetTaxisUseCase,
    private val createNewTaxi: ICreateNewTaxiUseCase
) : StateScreenModel<TaxiUiState>(TaxiUiState()), TaxiScreenInteractionListener  {

    init {
        getDummyTaxiData()
    }

    fun onTaxiNumberChange(number: String) {
        mutableState.update { it.copy(taxiNumberInPage = number) }
    }

    fun onSearchInputChange(searchQuery: String) {
        mutableState.update { it.copy(searchQuery = searchQuery) }
    }

    private fun getDummyTaxiData() {
        mutableState.update { it.copy(taxis = getTaxis.getTaxis().toUiState()) }
    }
    override fun onCancelCreateTaxiClicked() {
        mutableState.update { it.copy(isAddNewTaxiDialogVisible = false) }
    }

    override fun onTaxiPlateNumberChange(number: String) {
        mutableState.update {
            it.copy(addNewTaxiDialogUiState = it.addNewTaxiDialogUiState.copy(taxiPlateNumber = number))
        }
    }

    override fun onDriverUserNamChange(name: String) {
        mutableState.update { it.copy(addNewTaxiDialogUiState = it.addNewTaxiDialogUiState.copy(driverUserName = name)) }

    }

    override fun onCarModelChange(model: String) {
        mutableState.update { it.copy(addNewTaxiDialogUiState = it.addNewTaxiDialogUiState.copy(carModel = model)) }

    }

    override fun onCarColorSelected(color: CarColor) {
        mutableState.update {
            it.copy(addNewTaxiDialogUiState = it.addNewTaxiDialogUiState.copy(selectedCarColor = color))
        }
    }

    override fun onSeatsSelected(seats: Int) {
        mutableState.update { it.copy(addNewTaxiDialogUiState = it.addNewTaxiDialogUiState.copy(seats = seats)) }
    }

    override fun onCreateTaxiClicked() {
        coroutineScope.launch(Dispatchers.IO) {
            createNewTaxi.createTaxi(mutableState.value.addNewTaxiDialogUiState.toEntity())
        }
        mutableState.update { it.copy(isAddNewTaxiDialogVisible = false) }
    }

    override fun onAddNewTaxiClicked() {
        mutableState.update { it.copy(isAddNewTaxiDialogVisible = true) }
    }

}