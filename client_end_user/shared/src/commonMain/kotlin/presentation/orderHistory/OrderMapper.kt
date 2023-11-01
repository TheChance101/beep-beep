package presentation.orderHistory

import androidx.paging.PagingData
import androidx.paging.map
import domain.entity.FoodOrder
import domain.entity.Location
import domain.entity.Trip
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import presentation.orderFoodTracking.LocationUiState
import presentation.resturantDetails.toUIState

fun FoodOrder.toOrderHistoryUiState() = OrderHistoryUiState(
    meals = meals.joinToString(", ") { "${it.quality} ${it.name}" },
    restaurantName = restaurantName,
    restaurantImageUrl = restaurantImageUrl,
    totalPrice = totalPrice,
    currency = currency,
    createdAt = createdAt
)

fun Trip.toTripHistoryUiState(): TripHistoryUiState {
    return TripHistoryUiState(
        taxiPlateNumber = taxiPlateNumber ?: "",
        driverName = taxiDriverName ?: "",
        startPoint = startPoint.toLocationUiState(),
        destination = destination.toLocationUiState(),
        price = price,
        endDate = ""
    )
}

fun Location.toLocationUiState() = LocationUiState(latitude = latitude, longitude = longitude)


fun Flow<PagingData<FoodOrder>>.toOrderHistoryUiState(): Flow<PagingData<OrderHistoryUiState>> {
    return this.map { pagingData -> pagingData.map { it.toOrderHistoryUiState() } }
}

fun Flow<PagingData<Trip>>.toTripHistoryUiState(): Flow<PagingData<TripHistoryUiState>> {
    return this.map { pagingData -> pagingData.map { it.toTripHistoryUiState() } }
}
