package presentation.orderHistory

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.FoodOrder
import domain.entity.Trip
import domain.usecase.GetTransactionHistoryUseCase
import domain.usecase.IManageAuthenticationUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class OrderHistoryScreenModel(
    private val orderHistoryUseCase: GetTransactionHistoryUseCase,
    private val manageAuthentication: IManageAuthenticationUseCase
) : BaseScreenModel<OrderScreenUiState, OrderHistoryScreenUiEffect>(OrderScreenUiState()),
    OrderHistoryScreenInteractionListener {

    override val viewModelScope: CoroutineScope = coroutineScope

    init {
        checkIfLoggedIn()
    }

    private fun checkIfLoggedIn() {
        tryToExecute(
            { manageAuthentication.getAccessToken() },
            ::onCheckIfLoggedInSuccess,
            ::onCheckIfLoggedInError
        )
    }

    private fun onCheckIfLoggedInSuccess(accessToken: Flow<String>) {
        coroutineScope.launch {
            accessToken.collect { token ->
                if (token.isNotEmpty()) {
                    updateState { it.copy(isLoggedIn = true) }
                    getOrdersHistory()
                    getTripsHistory()
                } else {
                    updateState { it.copy(isLoggedIn = false) }
                }
            }
        }
    }

    private fun onCheckIfLoggedInError(errorState: ErrorState) {
        updateState { it.copy(isLoggedIn = false) }
    }

    private fun getOrdersHistory() {
        tryToExecute(
            { orderHistoryUseCase.getOrdersHistory() },
            ::onGetOrdersHistorySuccess,
            ::onError
        )
    }

    private fun getTripsHistory() {
        tryToExecute(
            { orderHistoryUseCase.getTripsHistory() },
            ::onGetTripsHistorySuccess,
            ::onError
        )
    }

    private fun onGetTripsHistorySuccess(tripsHistory: List<Trip>) {
        updateState { it.copy(tripsHistory = tripsHistory.map { it.toTripHistoryUiState() }) }
    }

    private fun onGetOrdersHistorySuccess(ordersHistory: List<FoodOrder>) {
        updateState { it.copy(ordersHistory = ordersHistory.map { it.toOrderHistoryUiState() }) }
    }

    private fun onError(errorState: ErrorState) {
        // catch errors here
    }

    override fun onClickTab(type: OrderScreenUiState.OrderSelectType) {
        updateState { it.copy(selectedType = type) }
    }

    override fun onClickLogin() {
        sendNewEffect(OrderHistoryScreenUiEffect.NavigateToLoginScreen)
    }
}
