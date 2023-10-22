package presentation.orderFoodTracking

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Location
import domain.usecase.ITrackOrdersUseCase
import kotlinx.coroutines.CoroutineScope
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

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

    init {
        getUserLocation()
    }

    private fun getUserLocation() {
        updateState { it.copy(isLoading = true) }
        tryToExecute(
            function = trackOrders::getUserOrderLocation,
            onSuccess = ::onGetUserLocationSuccess,
            onError = ::onGetUserLocationError,
        )
    }

    private fun onGetUserLocationSuccess(location: Location) {
        updateState { it.copy(userLocation = location.toUiState(), isLoading = false) }
    }

    private fun onGetUserLocationError(errorState: ErrorState) {
        updateState { it.copy(isLoading = false) }
    }
}