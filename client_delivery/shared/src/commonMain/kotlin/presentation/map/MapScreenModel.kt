package presentation.map

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Location
import domain.usecase.IManageLoginUserUseCase
import domain.usecase.IManageUserLocationUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class MapScreenModel(
    private val currentLocation: IManageUserLocationUseCase,
    private val manageLoginUser: IManageLoginUserUseCase,
):BaseScreenModel<MapScreenUiState,MapScreenUiEffect>(MapScreenUiState()),MapScreenInteractionsListener {

    override val viewModelScope: CoroutineScope = coroutineScope

    init {
        getUserName()
        getCurrentLocation()
        updateOrderState()
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
    private fun getCurrentLocation() {
        tryToCollect(
            function = currentLocation::trackCurrentLocation,
            onNewValue = ::onGetLiveLocationSuccess,
            onError = ::onError
        )
    }
    private fun updateOrderState() {
        viewModelScope.launch {
            delay(5000)
            updateState {
                it.copy(
                    orderState = OrderState.NEW_ORDER,
                    destinationLocation = LocationUiState(31.2001, 29.9187, "")
                )
            }
        }
    }
    private fun onGetLiveLocationSuccess(location: Location) {
        viewModelScope.launch {
            delay(2000)
            updateState {
                it.copy(
                    errorState = null,
                    currentLocation = location.toUiState()
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
            destinationLocation = LocationUiState(31.2001, 29.9187, "")
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