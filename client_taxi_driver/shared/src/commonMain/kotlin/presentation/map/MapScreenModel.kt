package presentation.map

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Location
import domain.entity.Order
import domain.usecase.IManageLocationUseCase
import domain.usecase.IManageOrderUseCase
import domain.usecase.LoginUserUseCase
import kotlinx.coroutines.launch
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class MapScreenModel(
    private val loginUserUseCase: LoginUserUseCase,
    private val order: IManageOrderUseCase,
    private val location: IManageLocationUseCase,
) : BaseScreenModel<MapScreenUiState, MapUiEffect>(MapScreenUiState()),
    MapScreenInteractionListener {

    init {
        getLiveLocation()
        findingNewOrder()
        getUserName()
    }

    private fun findingNewOrder() {
        tryToExecute(
            function = order::foundNewOrder,
            onSuccess = ::onFoundNewOrderSuccess,
            onError = ::onError
        )
    }

    private fun getUserName() {
        coroutineScope.launch {
            val username = loginUserUseCase.getUsername()
            updateState {
                it.copy(
                    userName = username,
                )
            }
        }
    }

    private fun onFoundNewOrderSuccess(order: Order) {
        updateState {
            it.copy(
                isLoading = false,
                isNewOrderFound = true,
                error = null,
                orderInfoUiState = order.toUiState()
            )
        }
    }

    private fun onError(errorState: ErrorState) {
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
        updateState {
            it.copy(
                isLoading = true,
                error = null,
                currentLocation = location.toUiState()
            )
        }
    }

    private fun onGetTaxiDriverNameSuccess(name: String) {
        updateState {
            it.copy(
                error = null,
                userName = name
            )
        }
    }


    override fun onClickAccept() {
        updateState {
            it.copy(
                isLoading = false,
                error = null,
                isNewOrderFound = false,
                isAcceptedOrder = true,
            )
        }
    }

    override fun onClickCancel() {
        updateState {
            it.copy(
                isLoading = true,
                isNewOrderFound = false,
                isAcceptedOrder = false,
                error = null,
                orderInfoUiState = OrderInfoUiState()
            )
        }
        findingNewOrder()
    }

    override fun onClickArrived() {
        updateState {
            it.copy(
                isLoading = false,
                error = null,
                orderInfoUiState = it.orderInfoUiState.copy(
                    isArrived = true
                )
            )
        }
    }

    override fun onClickDropOff() {
        updateState {
            it.copy(
                isLoading = true,
                isAcceptedOrder = false,
                error = null,
                orderInfoUiState = OrderInfoUiState()
            )
        }
        findingNewOrder()
    }

    override fun onClickCloseIcon() {
        sendNewEffect(MapUiEffect.PopUp)
    }
}