package presentation.order.orderHistory

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Order
import domain.usecase.IManageOrderUseCase
import kotlinx.coroutines.CoroutineScope
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.base.BaseScreenModel
import presentation.base.ErrorState
import presentation.order.OrderUiState
import presentation.order.toOrderMealUiState
import util.formatDateTime


// todo: need to pass restaurantId from previous screen
class OrderHistoryScreenModel(private val restaurantId: String) :
    BaseScreenModel<OrderHistoryScreenUiState, OrderHistoryScreenUiEffect>(OrderHistoryScreenUiState()),
    OrderHistoryScreenInteractionListener, KoinComponent {
    override val viewModelScope: CoroutineScope = coroutineScope
    private val manageOrderUseCase: IManageOrderUseCase by inject()

    init {
        getData()
    }

    private fun getData() {
        tryToExecute(
            ::callApi,
            ::onSuccess,
            ::onError
        )
    }

    private suspend fun callApi(): List<Order> {
        return when (state.value.selectedType) {
            OrderHistoryScreenUiState.OrderSelectType.FINISHED -> {
                manageOrderUseCase.getFinishedOrdersHistory(restaurantId)
            }
            OrderHistoryScreenUiState.OrderSelectType.CANCELLED -> {
                manageOrderUseCase.getCanceledOrdersHistory(restaurantId)
            }
        }
    }

    private fun onSuccess(orders: List<Order>) {
        updateState {
            it.copy(
                errorState = null,
                orders = orders.map { order -> order.toOrderHistoryUiState() }
            )
        }
    }

    private fun onError(errorState: ErrorState) {
        updateState { it.copy(errorState = errorState) }
    }

    override fun onClickBack() {
        sendNewEffect(OrderHistoryScreenUiEffect.Back)
    }

    override fun onClickTab(type: OrderHistoryScreenUiState.OrderSelectType) {
        updateState { it.copy(selectedType = type) }
        getData()
    }
}

private fun Order.toOrderHistoryUiState(): OrderUiState {
    return OrderUiState(
        id = id,
        orderMealUiStates = meals.map { it.toOrderMealUiState() },
        totalPrice = totalPrice,
        createdAt = createdAt.formatDateTime(),
    )
}


