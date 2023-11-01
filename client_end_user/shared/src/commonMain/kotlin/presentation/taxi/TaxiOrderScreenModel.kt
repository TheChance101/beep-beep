package presentation.taxi

import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel
import presentation.orderFoodTracking.OrderFoodTrackingUiEffect

class TaxiOrderScreenModel : BaseScreenModel<
        TaxiOrderUiState, TaxiOrderUiEffect>(TaxiOrderUiState()), TaxiOrderInteractionListener {
    override val viewModelScope: CoroutineScope = coroutineScope
    override fun onBackButtonClicked() {
        sendNewEffect(TaxiOrderUiEffect.NavigateBack)

    }

    override fun onSearchDestination() {
        sendNewEffect(TaxiOrderUiEffect.NavigateToSearch)
    }

    override fun onSearchInputChanged(keyword: String) {
        TODO("Not yet implemented")
    }

}
