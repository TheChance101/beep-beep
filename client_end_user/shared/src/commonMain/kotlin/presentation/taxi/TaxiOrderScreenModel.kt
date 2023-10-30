package presentation.taxi

import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel

class TaxiOrderScreenModel : BaseScreenModel<
        TaxiOrderUiState, TaxiOrderUiEffect>(TaxiOrderUiState()), TaxiOrderInteractionListener {
    override val viewModelScope: CoroutineScope = coroutineScope
    override fun onBackButtonClicked() {
        TODO("Not yet implemented")
    }

    override fun onSearchInputChanged(keyword: String) {
        TODO("Not yet implemented")
    }

}
