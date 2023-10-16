package presentation.map

import domain.entity.Location
import domain.entity.Order
import domain.usecase.IIdentityUseCase
import domain.usecase.IManageLocationUseCase
import domain.usecase.IManageOrderUseCase
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class MapScreenModel(
    private val order: IManageOrderUseCase,
    private val location: IManageLocationUseCase,
    private val identity: IIdentityUseCase,
) : BaseScreenModel<MapScreenUiState, MapUiEffect>(MapScreenUiState()),
    MapScreenInteractionListener {

    init {
        getLiveLocation()
        getTaxiDriverName()
        findingNewOrder()
    }

    private fun findingNewOrder() {
        tryToExecute(
            function = order::foundNewOrder,
            onSuccess = ::onFoundNewOrderSuccess,
            onError = ::onError
        )
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


    private fun getTaxiDriverName() {
        tryToExecute(
            function = identity::getTaxiDriverName,
            onSuccess = ::onGetTaxiDriverNameSuccess,
            onError = ::onError,
        )
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