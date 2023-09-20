package presentation.orderHistory

import cafe.adriel.voyager.core.model.coroutineScope
import domain.usecase.GetOrderHistoryUseCase
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class OrderHistoryScreenModel(private val orderHistoryUseCase: GetOrderHistoryUseCase) :
    BaseScreenModel<OrderScreenUiState, OrderHistoryScreenUiEffect>(OrderScreenUiState()),
    OrderHistoryScreenInteractionListener {

    override val viewModelScope: CoroutineScope = coroutineScope

    init {
        getOrdersHistory()
        getTripsHistory()
    }

    private fun getOrdersHistory() {
        tryToExecute(
            { orderHistoryUseCase.getOrdersHistory().map { it.toOrderHistoryUiState() } },
            ::onGetOrdersHistorySuccess,
            ::onError
        )
    }

    private fun getTripsHistory() {
        tryToExecute(
            { orderHistoryUseCase.getTripsHistory().map { it.toTripHistoryUiState() } },
            ::onGetTripsHistorySuccess,
            ::onError
        )
    }

    private fun onGetTripsHistorySuccess(tripsHistory: List<TripHistoryUiState>) {
        updateState { it.copy(tripsHistory = tripsHistory) }
    }

    private fun onGetOrdersHistorySuccess(ordersHistory: List<OrderHistoryUiState>) {
        updateState { it.copy(ordersHistory = ordersHistory) }
    }

    private fun onError(errorState: ErrorState) {
        // catch errors here
    }

    override fun onClickTab(type: OrderScreenUiState.OrderSelectType) {
        updateState { it.copy(selectedType = type) }
    }
}