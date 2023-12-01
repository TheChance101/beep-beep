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
        getDriverName()
        getCurrentLocation()
        getOrder()
    }

    private fun getDriverName() {
        viewModelScope.launch {
            val username = manageLoginUser.getUsername()
            updateState { it.copy(username = username) }
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
            delay(5000)
            updateState {
                it.copy(
                    errorState = null,
                    deliveryLocation = location.toUiState()
                )
            }
            if (state.value.orderState == OrderState.RECEIVED) {
                broadcastLocation(state.value.tripId, location.toUiState())
            }
        }
    }

    private fun onError(errorState: ErrorState) {
        updateState {
            it.copy(
                orderState = OrderState.LOADING,
                errorState = errorState,
                isLoading = false,
                isButtonEnabled = true
            )
        }
    }

    override fun onAcceptClicked() {
        updateState { it.copy(isLoading = true, isButtonEnabled = false) }
        viewModelScope.launch {
            currentLocation.trackCurrentLocation()
        }
        tryToExecute(
            function = { manageOrderUseCase.updateTrip(state.value.tripId) },
            onSuccess = ::onUpdateOrderSuccess,
            onError = ::onError
        )
    }

    override fun onReceivedClicked() {
        updateState { it.copy(isLoading = true, isButtonEnabled = false) }
        tryToExecute(
            function = { manageOrderUseCase.updateTrip(state.value.tripId) },
            onSuccess = ::onUpdateOrderSuccess,
            onError = ::onError
        )
    }

    override fun onDeliveredClicked() {
        updateState { it.copy(isLoading = true, isButtonEnabled = false) }
        tryToExecute(
            function = { manageOrderUseCase.updateTrip(state.value.tripId) },
            onSuccess = ::onUpdateOrderSuccess,
            onError = ::onError
        )
    }

    private fun onUpdateOrderSuccess(order: Order) {
        val currentStatus = when (order.tripStatus) {
            TripStatus.PENDING -> OrderState.LOADING
            TripStatus.APPROVED -> {
                viewModelScope.launch {
                    val destinationInKM = manageOrderUseCase.calculateDistance(
                        order.startPoint.latitude,
                        order.startPoint.longitude,
                        order.destination.latitude,
                        order.destination.longitude,
                    )
                    val timeInMin = manageOrderUseCase.calculateTimeInMinutes(destinationInKM)
                    updateState {
                        it.copy(
                            orderDistance = destinationInKM.toInt(),
                            orderDuration = timeInMin.toInt()
                        )
                    }
                }
                OrderState.ACCEPTED
            }

            TripStatus.RECEIVED -> {
                OrderState.RECEIVED
            }

            TripStatus.FINISHED -> {
                sendNewEffect(MapScreenUiEffect.CloseMap)
                OrderState.LOADING
            }
        }
        updateState { mapScreenUiState ->
            mapScreenUiState.copy(
                orderState = currentStatus,
                isLoading = false,
                isButtonEnabled = true
            )
        }
    }

    private fun broadcastLocation(tripId: String, location: LocationUiState) {
        tryToExecute(
            function = { manageOrderUseCase.broadcastLocation(location.toEntity(), tripId) },
            onSuccess = { onBroadCastLocationSuccess(location) },
            onError = ::onError
        )
    }

    private fun onBroadCastLocationSuccess(location: LocationUiState) {
        println("New emitted Location is $location ")
    }


    override fun onRejectClicked() {
        viewModelScope.launch {
            updateState { it.copy(orderState = OrderState.LOADING) }
        }
    }

    override fun onCloseClicked() {
        viewModelScope.launch { currentLocation.stopTracking() }
        sendNewEffect(MapScreenUiEffect.CloseMap)
    }
}