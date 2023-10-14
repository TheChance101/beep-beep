package presentation.map

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Order
import domain.usecase.LoginUserUseCase
import domain.usecase.ManageOrderUseCase
import kotlinx.coroutines.launch
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class MapScreenModel(
    private val order: ManageOrderUseCase,
    private val loginUserUseCase: LoginUserUseCase,
) : BaseScreenModel<MapScreenUiState, MapUiEffect>(MapScreenUiState()),
    MapScreenInteractionListener {

    init {
        findingNewOrder()
        getUserName()
    }

    private fun findingNewOrder() {
        tryToExecute(
            function = order::foundNewOrder,
            onSuccess = ::onFoundNewOrderSuccess,
            onError = ::onFoundNewOrderFailed
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
                error = null
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
                orderInfoUiState = it.orderInfoUiState.copy(
                    isArrived = false
                )
            )
        }
        findingNewOrder()
    }

    override fun onClickCloseIcon() {
        sendNewEffect(MapUiEffect.PopUp)
    }
}