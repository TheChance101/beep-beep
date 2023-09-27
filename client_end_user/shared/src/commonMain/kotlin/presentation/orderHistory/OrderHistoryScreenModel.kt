package presentation.orderHistory

import cafe.adriel.voyager.core.model.coroutineScope
import domain.usecase.GetOrderHistoryUseCase
import domain.usecase.IManageAuthenticationUseCase
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class OrderHistoryScreenModel(
    private val orderHistoryUseCase: GetOrderHistoryUseCase,
    private val manageAuthentication: IManageAuthenticationUseCase
) : BaseScreenModel<OrderScreenUiState, OrderHistoryScreenUiEffect>(OrderScreenUiState()),
    OrderHistoryScreenInteractionListener {

    override val viewModelScope: CoroutineScope = coroutineScope

    init {
        checkIfLoggedIn()
        getOrdersHistory()
        getTripsHistory()
    }

    private fun checkIfLoggedIn() {
        tryToExecute(
            { manageAuthentication.getAccessToken() },
            ::onCheckIfLoggedInSuccess,
            ::onCheckIfLoggedInError
        )
    }

    private fun onCheckIfLoggedInSuccess(accessToken: String) {
        if (accessToken.isNotEmpty()) {
            updateState { it.copy(isLoggedIn = true) }
        } else {
            updateState { it.copy(isLoggedIn = false) }
        }
    }

    private fun onCheckIfLoggedInError(errorState: ErrorState) {
        updateState { it.copy(isLoggedIn = false) }
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

    override fun onClickLogin() {
        sendNewEffect(OrderHistoryScreenUiEffect.NavigateToLoginScreen)
    }
}