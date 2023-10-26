package presentation.orderHistory

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import presentation.base.ErrorState

data class OrderScreenUiState(
    val selectedType: OrderSelectType = OrderSelectType.MEALS,
    val ordersHistory: Flow<PagingData<OrderHistoryUiState>> = emptyFlow(),
    val tripsHistory:  Flow<PagingData<TripHistoryUiState>> = emptyFlow(),

    val isLoggedIn: Boolean = false,
    val isLoading: Boolean = false,
    val error: ErrorState? = null
) {
    enum class OrderSelectType {
        MEALS,
        TRIPS
    }
}

data class OrderHistoryUiState(
    val meals: String = "",
    val restaurantName: String = "",
    val restaurantImageUrl: String = "",
    val totalPrice: Double = 0.0,
    val currency: String = "",
    val createdAt: String = "",
)

data class TripHistoryUiState(
    val taxiPlateNumber: String = "",
    val driverName: String = "",
    val startPoint: LocationUiState = LocationUiState(),
    val destination: LocationUiState = LocationUiState(),
    val price: Double = 0.0,
    val endDate: String = "",
) {
    data class LocationUiState(val latitude: Double = 0.0, val longitude: Double = 0.0)
}


