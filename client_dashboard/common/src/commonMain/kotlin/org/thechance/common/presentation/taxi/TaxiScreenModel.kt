package org.thechance.common.presentation.taxi

import org.thechance.common.domain.entity.Taxi
import org.thechance.common.domain.usecase.IFindTaxiByUserNameUseCase
import org.thechance.common.domain.usecase.IGetTaxisUseCase
import org.thechance.common.presentation.base.BaseScreenModel
import org.thechance.common.presentation.util.ErrorState

class TaxiScreenModel(
    private val getTaxis: IGetTaxisUseCase,
    private val getTaxiByUserName: IFindTaxiByUserNameUseCase
) : BaseScreenModel<TaxiUiState, TaxiUiEffect>(TaxiUiState()), TaxiInteractionListener {

    init {
        getDummyTaxiData()
    }

    override fun onTaxiNumberChange(number: String) {
        updateState { it.copy(taxiNumberInPage = number) }
    }

    override fun onSearchInputChange(searchQuery: String) {
        updateState { it.copy(searchQuery = searchQuery) }
        findTaxiByUsername(searchQuery)
    }

    private fun getDummyTaxiData() {
        tryToExecute(getTaxis::getTaxis, ::onGetTaxisSuccessfully, ::onError)
    }

    private fun findTaxiByUsername(username: String) {
        tryToExecute({ getTaxiByUserName.findTaxiByUsername(username) }, ::onFindTaxiSuccessfully, ::onError)
    }

    private fun onFindTaxiSuccessfully(taxis: List<Taxi>) {
        updateState { it.copy(taxis = taxis.toUiState(), isLoading = false) }
    }

    private fun onGetTaxisSuccessfully(taxis: List<Taxi>) {
        updateState { it.copy(taxis = taxis.toUiState(), isLoading = false) }
    }


    private fun onError(error: ErrorState) {
        updateState { it.copy(error = error, isLoading = false) }
    }

}