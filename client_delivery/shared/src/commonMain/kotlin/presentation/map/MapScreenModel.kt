package presentation.map

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Location
import domain.entity.Order
import domain.usecase.IManageOrderUseCase
import domain.usecase.IManageUserLocationUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class MapScreenModel(
    private val currentLocation: IManageUserLocationUseCase,
    private val manageOrderUseCase: IManageOrderUseCase,
):BaseScreenModel<MapScreenUiState,MapScreenUiEffect>(MapScreenUiState()),MapScreenInteractionsListener {

    override val viewModelScope: CoroutineScope = coroutineScope

    init {
        getCurrentLocation()
        getOrder()
    }
    private fun getOrder() {
        updateState { it.copy(orderState = OrderState.LOADING) }
        tryToExecute(
            function = manageOrderUseCase::getOrders,
            onSuccess = ::onGetOrderSuccess,
            onError = ::onError
        )
    }

    private fun onGetOrderSuccess(orders: Flow<Order>) {
        viewModelScope.launch {
            orders.collect { order ->
                updateState { mapScreenUiState ->
                    mapScreenUiState.copy(
                        orderUiState = order.toUiState(),
                        orderState = OrderState.NEW_ORDER
                    )
                }
            }
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
        updateState { it.copy(
            orderState = OrderState.ACCEPTED,
            // todo replace this with the restaurant actual location
            orderUiState = OrderUiState(
                destinationLocation = LocationUiState(
                    31.2001,
                    29.9187,
                    ""
                )
            )
        ) }
    }

    override fun onRejectClicked() {
        viewModelScope.launch {
            updateState { it.copy(orderState = OrderState.LOADING) }
            delay(2000) // just for simulation
            updateState { it.copy(orderState = OrderState.NEW_ORDER) }
        }
    }

    override fun onReceivedClicked() {
      updateState { it.copy(orderState = OrderState.RECEIVED) }
    }

    override fun onDeliveredClicked() {
        viewModelScope.launch {
            currentLocation.startTracking()
        }
        viewModelScope.launch {
            updateState { it.copy(orderState = OrderState.DELIVERED) }
            updateState { it.copy(orderState = OrderState.LOADING) }
            delay(2000)
            updateState { it.copy(orderState = OrderState.NEW_ORDER) }
        }
    }

    override fun onCloseClicked() {
        viewModelScope.launch {
            currentLocation.stopTracking()
        }
        sendNewEffect(MapScreenUiEffect.CloseMap)
    }

}