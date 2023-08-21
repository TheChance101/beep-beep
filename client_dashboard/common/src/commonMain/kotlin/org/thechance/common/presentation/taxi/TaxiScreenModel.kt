package org.thechance.common.presentation.taxi

import cafe.adriel.voyager.core.model.StateScreenModel
import kotlinx.coroutines.flow.update
import org.thechance.common.domain.usecase.IGetTaxisUseCase


class TaxiScreenModel(
    private val getTaxis: IGetTaxisUseCase
) : StateScreenModel<TaxiUiState>(TaxiUiState()) {

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
        mutableState.update {
            it.copy(taxis = getTaxis.getTaxis().toUiState(),)
        }
    }

}