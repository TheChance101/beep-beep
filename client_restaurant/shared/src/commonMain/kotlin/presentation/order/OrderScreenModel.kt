package presentation.order

import cafe.adriel.voyager.core.model.coroutineScope
import domain.usecase.IManageOrderUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class OrderScreenModel :
    BaseScreenModel<OrderScreenUiState, OrderScreenUiEffect>(OrderScreenUiState()),
    OrderScreenInteractionListener {
    override val viewModelScope: CoroutineScope = coroutineScope

    private val getNewOrdersUseCase: IManageOrderUseCase by inject()
    override fun onClickBack() {
        sendNewEffect(OrderScreenUiEffect.Back)
    }

    init {
        println("LLLLLLLLLLLLLLLL")
        getNewOrders()
        viewModelScope.launch {
            println("LLLLLLLLLLLLLLLLLLLLLLLLLLLL ${getNewOrdersUseCase.getOrders("aaaaa")}")
        }
    }

    private fun getNewOrders() {
        tryToExecute(
            { getNewOrdersUseCase.getOrders("7c3d631e-6d49-48c9-9f91-9426ec559eb1").map { it.toOrderUiState() } },
            ::onGetNewOrdersSuccess,
            ::onGetNewOrdersError
        )
    }

    private fun onGetNewOrdersSuccess(orders: List<OrderUiState>) {
        updateState { it.copy(orders = orders) }
        println("AAAAAAAAAA ${state.value.orders}")

    }

    private fun onGetNewOrdersError(errorState: ErrorState) {
        println("AAAAAAAAAAAAAA $errorState")
    }
}