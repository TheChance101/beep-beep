package presentation.orderHistory

import cafe.adriel.voyager.core.model.coroutineScope
import domain.usecase.GetOrderHistoryUseCase
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel

class OrderScreenModel(private val orderHistoryUseCase: GetOrderHistoryUseCase) :
    BaseScreenModel<OrderScreenUiState, OrderScreenUiEffect>(OrderScreenUiState()),
    OrderScreenInteractionListener {

    override val viewModelScope: CoroutineScope = coroutineScope



}