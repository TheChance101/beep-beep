package presentation.order

import cafe.adriel.voyager.core.model.coroutineScope
import domain.usecase.IManageOrderUseCase
import kotlinx.coroutines.CoroutineScope
import org.koin.core.component.inject
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class OrderScreenModel :
    BaseScreenModel<OrderScreenUiState, OrderScreenUiEffect>(OrderScreenUiState()),
    OrderScreenInteractionListener {
    override val viewModelScope: CoroutineScope = coroutineScope

    private val manageOrdersUseCase: IManageOrderUseCase by inject()
    override fun onClickBack() {
        sendNewEffect(OrderScreenUiEffect.Back)
    }

    init {
        getAllActiveOrders("7c3d631e-6d49-48c9-9f91-9426ec559eb1")
        /*
        getInCookingOrders("7c3d631e-6d49-48c9-9f91-9426ec559eb1")
        getPendingOrders("7c3d631e-6d49-48c9-9f91-9426ec559eb1")
         */
    }

    private fun getAllActiveOrders(restaurantId: String) {
        tryToExecute(
            { manageOrdersUseCase.getAllActiveOrders(restaurantId).map { it.toOrderUiState() } },
            ::onGetActiveOrdersSuccess,
            ::onGetActiveOrdersError
        )
    }

    private fun onGetActiveOrdersSuccess(orders: List<OrderUiState>) {
        updateState { it.copy(activeOrders = orders) }
    }

    private fun onGetActiveOrdersError(errorState: ErrorState) {
        println("Error is $errorState")
    }

    /*
    private fun getPendingOrders(restaurantId: String) {
        tryToExecute(
            { manageOrdersUseCase.getPendingOrders(restaurantId).map { it.toOrderUiState() } },
            ::onGetPendingOrdersSuccess,
            ::onGetPendingOrdersError
        )
    }

    private fun onGetPendingOrdersSuccess(pendingOrders: List<OrderUiState>) {
        updateState { it.copy(inCookingOrders = pendingOrders) }
    }

    private fun onGetPendingOrdersError(errorState: ErrorState) {
        println("Error is $errorState")
    }

    private fun getInCookingOrders(restaurantId: String) {
        tryToExecute(
            { manageOrdersUseCase.getInCookingOrders(restaurantId).map { it.toOrderUiState() } },
            ::onGetCookingSuccess,
            ::onGetCookingError
        )
    }

    private fun onGetCookingSuccess(inCookingOrders: List<OrderUiState>) {
        updateState { it.copy(inCookingOrders = inCookingOrders) }
    }

    private fun onGetCookingError(errorState: ErrorState) {
        println("Error is $errorState")
    }

 */
}