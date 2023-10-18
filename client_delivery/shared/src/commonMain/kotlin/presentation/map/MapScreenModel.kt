package presentation.map

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Location
import domain.entity.Order
import domain.usecase.IManageLoginUserUseCase
import domain.usecase.IManageOrderUseCase
import domain.usecase.IManageUserLocationUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class MapScreenModel(
    private val currentLocation: IManageUserLocationUseCase,
    private val manageLoginUser: IManageLoginUserUseCase,
    private val manageOrderUseCase: IManageOrderUseCase,
):BaseScreenModel<MapScreenUiState,MapScreenUiEffect>(MapScreenUiState()),MapScreenInteractionsListener {

    override val viewModelScope: CoroutineScope = coroutineScope

    init {
        getUserName()
        getCurrentLocation()
        getOrder()
    }
     private fun getUserName() {
        viewModelScope.launch {
            val username = manageLoginUser.getUsername()
            updateState {
                it.copy(
                    username = username,
                )
            }
        }
     }

    private fun getOrder() {
        updateState { it.copy(orderState = OrderState.LOADING) }
        tryToCollect(
            function = manageOrderUseCase::getOrders,
            onNewValue = ::onGetOrderSuccess,
            onError = ::onError
        )
    }

    private fun onGetOrderSuccess(order: Order) {
        println("orderUiState: ${order.toUiState()}")
        updateState { mapScreenUiState ->
            mapScreenUiState.copy(
                orderUiState = order.toUiState(),
                orderState = OrderState.NEW_ORDER
            )

        }
    }

    private fun getCurrentLocation() {
        tryToCollect(
            function = currentLocation::trackCurrentLocation,
            onNewValue = ::onGetLiveLocationSuccess,
            onError = ::onError
        )
    }

    private fun onGetLiveLocationSuccess(location: Location) {
        viewModelScope.launch {
            delay(2000)
            updateState {
                it.copy(
                    errorState = null,
                    orderUiState = OrderUiState(restaurantLocation = location.toUiState())
                )
            }
        }
    }

    private fun onError(errorState: ErrorState) {
        updateState {
            it.copy(
                orderState = OrderState.LOADING,
                errorState = errorState,
            )
        }
    }

    override fun onAcceptClicked() {
        tryToExecute(
            function = { manageOrderUseCase.acceptOrder("","","") },
            onSuccess = ::onAcceptOrderSuccess,
            onError = ::onError
        )
    }

    private fun onAcceptOrderSuccess(order: Order) {
        updateState { mapScreenUiState ->
            mapScreenUiState.copy(
                orderUiState = order.toUiState(),
                orderState = OrderState.ACCEPTED
            )
        }
    }

    override fun onRejectClicked() {
        viewModelScope.launch {
            updateState { it.copy(orderState = OrderState.LOADING) }
        }
    }

    override fun onReceivedClicked() {
        tryToExecute(
            function = { manageOrderUseCase.updateOrderAsReceived("1", "1") },
            onSuccess = ::onUpdateOrderAsReceivedSuccess,
            onError = ::onError
        )
    }

    private fun onUpdateOrderAsReceivedSuccess(order: Order) {
        updateState { mapScreenUiState ->
            mapScreenUiState.copy(
                orderUiState = order.toUiState(),
                orderState = OrderState.RECEIVED
            )
        }
    }

    override fun onDeliveredClicked() {
        tryToExecute(
            function = { manageOrderUseCase.updateOrderAsDelivered("1", "1") },
            onSuccess = ::onUpdateOrderAsDeliveredSuccess,
            onError = ::onError
        )
    }

    private fun onUpdateOrderAsDeliveredSuccess(order: Order) {
        updateState { mapScreenUiState ->
            mapScreenUiState.copy(
                orderUiState = order.toUiState(),
                orderState = OrderState.DELIVERED
            )
        }
    }

    override fun onCloseClicked() {
        viewModelScope.launch {
            currentLocation.stopTracking()
        }
        sendNewEffect(MapScreenUiEffect.CloseMap)
    }

}