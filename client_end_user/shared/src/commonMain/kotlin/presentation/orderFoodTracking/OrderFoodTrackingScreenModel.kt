package presentation.orderFoodTracking

import cafe.adriel.voyager.core.model.coroutineScope
import domain.usecase.ITrackOrdersUseCase
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel

class OrderFoodTrackingScreenModel(
    private val orderId: String,
    private val tripId: String,
    private val trackOrders: ITrackOrdersUseCase,
) : BaseScreenModel<OrderFoodTrackingUiState, OrderFoodTrackingUiEffect>(OrderFoodTrackingUiState()),
    OrderFoodTrackingInteractionListener {
    override val viewModelScope: CoroutineScope = coroutineScope
    override fun onBackButtonClicked() {
        sendNewEffect(OrderFoodTrackingUiEffect.NavigateBack)
    }
}