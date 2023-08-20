package org.thechance.common.presentation.taxi

import cafe.adriel.voyager.core.model.StateScreenModel
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.thechance.common.domain.usecase.IGetTaxisUseCase
import org.thechance.common.domain.usecase.IGetUsersUseCase


class TaxiScreenModel(

) : StateScreenModel<TaxiUiState>(TaxiUiState()) , KoinComponent {

    private val getTaxis: IGetTaxisUseCase by inject()

    init {
        println("TaxiScreenModel init")
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