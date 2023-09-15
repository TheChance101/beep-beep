package presentation.map

import domain.entity.Order
import domain.usecase.ManageOrderUseCase
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class MapScreenModel(
    private val order: ManageOrderUseCase,
) : BaseScreenModel<MapScreenUiState, MapUiEffect>(MapScreenUiState()),
    MapScreenInteractionListener {

    init {
        foundNewOrder()
    }

    private fun foundNewOrder() {
        tryToExecute(
            function = order::foundNewOrder,
            onSuccess = ::onFoundNewOrderSuccess,
            onError = ::onFoundNewOrderFailed
        )
    }

    private fun onFoundNewOrderSuccess(order: Order) {
        updateState {
            it.copy(
                isLoading = false,
                isNewOrderFound = true,
                error = null,
                orderInfoUiState = it.orderInfoUiState.copy(
                    name = order.passengerName,
                    dropOffAddress = order.address,
                )
            )
        }
    }

    private fun onFoundNewOrderFailed(errorState: ErrorState) {
        updateState {
            it.copy(
                isLoading = false,
                isNewOrderFound = false,
                error = errorState,
            )
        }
    }

    override fun onClickAccept() {
    }

    override fun onClickCancel() {
        updateState {
            it.copy(
                isLoading = true,
                isNewOrderFound = false,
                error = null
            )
        }
        foundNewOrder()
    }

    override fun onClickArrived() {
    }

    override fun onClickDropOff() {
        updateState {
            it.copy(
                isLoading = true,
                isNewOrderFound = false,
                error = null
            )
        }
        foundNewOrder()
    }
}