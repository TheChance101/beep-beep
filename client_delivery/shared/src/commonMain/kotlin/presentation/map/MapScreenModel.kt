package presentation.map

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Location
import domain.entity.Order
import domain.entity.TripStatus
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
) : BaseScreenModel<MapScreenUiState, MapScreenUiEffect>(MapScreenUiState()),
    MapScreenInteractionsListener {

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
        val newOrder = order.toUiState()
        updateState { mapScreenUiState ->
            mapScreenUiState.copy(
                orderUiState = newOrder,
                orderState = OrderState.NEW_ORDER,
                tripId = newOrder.orderId,
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
                    orderUiState = it.orderUiState.copy(restaurantLocation = location.toUiState())
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

    //todo static taxi id just for now till end point is ready from backend
    override fun onAcceptClicked() {
        viewModelScope.launch {
            currentLocation.trackCurrentLocation()
        }
        tryToExecute(
            function = {
                manageOrderUseCase.updateTrip("654c0c7d066fac5a2fb26ea8", state.value.tripId)
            },
            onSuccess = ::onUpdateOrderSuccess,
            onError = ::onError
        )
    }

    //todo static taxi id just for now till end point is ready from backend
    override fun onReceivedClicked() {
        tryToExecute(
            function = {
                manageOrderUseCase.updateTrip("654c0c7d066fac5a2fb26ea8", state.value.tripId)
            },
            onSuccess = ::onUpdateOrderSuccess,
            onError = ::onError
        )
    }

    //todo static taxi id just for now till end point is ready from backend
    override fun onDeliveredClicked() {
        tryToExecute(
            function = {
                manageOrderUseCase.updateTrip("654c0c7d066fac5a2fb26ea8", state.value.tripId)
            },
            onSuccess = ::onUpdateOrderSuccess,
            onError = ::onError
        )
    }

    private fun onUpdateOrderSuccess(order: Order) {
        val currentStatus = when (order.tripStatus) {
            TripStatus.PENDING -> OrderState.LOADING
            TripStatus.APPROVED -> OrderState.ACCEPTED
            TripStatus.RECEIVED -> OrderState.RECEIVED
            TripStatus.FINISHED -> {
                sendNewEffect(MapScreenUiEffect.CloseMap)
                OrderState.LOADING
            }
        }
        updateState { mapScreenUiState ->
            mapScreenUiState.copy(orderState = currentStatus)
        }
    }

    override fun onRejectClicked() {
        viewModelScope.launch {
            updateState { it.copy(orderState = OrderState.LOADING) }
        }
    }


    override fun onCloseClicked() {
        viewModelScope.launch {
            currentLocation.stopTracking()
        }
        sendNewEffect(MapScreenUiEffect.CloseMap)
    }

}