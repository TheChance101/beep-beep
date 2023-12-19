package presentation.map

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Location
import domain.entity.Trip
import domain.usecase.IManageLocationUseCase
import domain.usecase.IManageTripUseCase
import domain.usecase.LoginUserUseCase
import kotlinx.coroutines.launch
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class MapScreenModel(
    private val loginUserUseCase: LoginUserUseCase,
    private val manageTrip: IManageTripUseCase,
    private val location: IManageLocationUseCase,
) : BaseScreenModel<MapScreenUiState, MapUiEffect>(MapScreenUiState()),
    MapScreenInteractionListener {

    init {
        findingNewOrder()
        getLiveLocation()
        getUserName()
    }

    private fun findingNewOrder() {
        println("findingNewOrder")
        tryToCollect(
            function = manageTrip::findNewTrip,
            onNewValue = ::onFoundNewOrderSuccess,
            onError = ::onError
        )
    }

    private fun getUserName() {
        coroutineScope.launch {
            val username = loginUserUseCase.getUsername()
            updateState {
                it.copy(
                    driverName = username,
                )
            }
        }
        println("getUserName: ${state.value.driverName}")
    }

    private fun onFoundNewOrderSuccess(order: Trip) {
        println("onFoundNewOrderSuccess: $order")
        println("state.value.tripInfoUiState:${state.value.tripInfoUiState}")
        updateState {
            it.copy(
                isLoading = false,
                isNewOrderFound = true,
                error = null,
                tripInfoUiState = order.toUiState()
            )
        }
    }

    private fun onError(errorState: ErrorState) {
        println("onEeeeeeeeeeeerror: $errorState")
        updateState {
            it.copy(
                isLoading = false,
                error = errorState,
            )
        }
    }


    private fun getLiveLocation() {
        tryToCollect(
            function = location::trackCurrentLocation,
            onNewValue = ::onGetLiveLocationSuccess,
            onError = ::onError,
        )
    }

    private fun onGetLiveLocationSuccess(location: Location) {
        updateState { mapScreenUiState ->
            mapScreenUiState.copy(
                isLoading = true,
                error = null,
                currentLocation = location.toUiState()
            ).also {
                if (it.isAcceptedOrder) {
                    sendLocationIfTripAccepted(
                        it.currentLocation.toEntity(),
                        it.tripInfoUiState.id
                    )
                }
            }
        }
    }

    private fun sendLocationIfTripAccepted(location: Location, tripId: String) {
        tryToExecute(
            function = { manageTrip.updateTripLocation(location, tripId) },
            onSuccess = { println("update trip location success") },
            onError = ::onError,
        )
    }

    override fun onClickAccept() {
        updateState { mapScreenUiState ->
            mapScreenUiState.copy(
                isLoading = false, error = null,
                isNewOrderFound = false, isAcceptedOrder = true,
            )
        }
        updateTrip()
    }

    override fun onClickCancel() {
        updateState {
            it.copy(
                isLoading = true,
                error = null,
                isNewOrderFound = false,
                isAcceptedOrder = false,
                tripInfoUiState = TripInfoUiState()
            )
        }
        findingNewOrder()
    }

    override fun onClickArrived() {
        updateState { it.copy(isLoading = false, error = null,
                tripInfoUiState = it.tripInfoUiState.copy(isArrived = true)
            )
        }
        updateTrip()
    }

    private fun updateTrip() {
        println("///////////////////////trip ID:${state.value.tripInfoUiState.id}///////////////////////")
        tryToExecute(
            function = { manageTrip.updateTripStatus(state.value.tripInfoUiState.id) },
            onSuccess = { println("update trip success") },
            onError = ::onError
        )
    }

    override fun onClickDropOff() {
        updateState {
            it.copy(
                isLoading = true, error = null,
                isAcceptedOrder = false, tripInfoUiState = TripInfoUiState()
            )
        }
        updateTrip()
        findingNewOrder()
    }

    override fun onClickCloseIcon() {
        sendNewEffect(MapUiEffect.PopUp)
    }
}