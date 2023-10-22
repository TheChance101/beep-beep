package presentation.orderFoodTracking

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Location
import domain.usecase.IGetUserLocationUseCase
import domain.usecase.ITrackOrdersUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class OrderFoodTrackingScreenModel(
    private val orderId: String,
    private val tripId: String,
    private val trackOrders: ITrackOrdersUseCase,
    private val getUserLocation: IGetUserLocationUseCase,
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
        tryToCollect(
            function = getUserLocation::trackCurrentLocation,
            onNewValue = ::onGetUserLocationSuccess,
            onError = ::onGetUserLocationError,
        )
    }

    private fun onGetUserLocationSuccess(location: Location) {
        viewModelScope.launch {
            delay(2000)
            updateState { it.copy(userLocation = location.toUiState()) }
        }

    }

    private fun onGetUserLocationError(errorState: ErrorState) {

    }
}