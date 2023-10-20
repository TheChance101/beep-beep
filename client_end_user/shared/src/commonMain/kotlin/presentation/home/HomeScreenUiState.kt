package presentation.home

import domain.entity.DeliveryRide
import domain.entity.FoodOrder
import domain.entity.PriceLevel
import domain.entity.TaxiRide
import domain.entity.Trip
import domain.entity.TripStatus
import presentation.cuisines.CuisineUiState

data class HomeScreenUiState(
    val offers: List<OfferUiState> = emptyList(),
    val isLoading: Boolean = false,
    val recommendedCuisines: List<CuisineUiState> = emptyList(),
    val favoriteRestaurants: List<RestaurantUiState> = emptyList(),
    val liveOrders: InProgressOrderUiState = InProgressOrderUiState(),
    val lastOrder: OrderUiState = OrderUiState(),
    val user: UserUiState = UserUiState(),
    val showCart: Boolean = false,
    val isLoggedIn: Boolean = false,
    val isMoreCuisine: Boolean = false,
    val maxCuisinesInHome: Int = 4,
) {
    val hasLiveOrders: Boolean
        get() = liveOrders.taxiRides.isNotEmpty() ||
                liveOrders.deliveryOrders.isNotEmpty() ||
                liveOrders.foodOrders.isNotEmpty()

    fun getOfferImages(): List<String> {
        return this.offers.map { it.image }
    }
}

data class InProgressOrderUiState(
    val taxiRides: List<TaxiRideUiState> = emptyList(),
    val deliveryOrders: List<DeliveryOrderUiState> = emptyList(),
    val foodOrders: List<FoodOrderUiState> = emptyList(),
)

data class TaxiRideUiState(
    val tripId: String = "",
    val taxiColor: String = "",
    val taxiPlateNumber: String = "",
    val rideStatus: Int = TripStatus.PENDING.statusCode,
    val rideEstimatedTime: Int = 30,
)

data class DeliveryOrderUiState(
    val tripId: String = "",
    val restaurantName: String = "",
    val orderStatus: Int = TripStatus.PENDING.statusCode,
)

data class FoodOrderUiState(
    val orderId: String = "",
    val restaurantName: String = "",
    val orderStatus: Int = FoodOrder.OrderStatusInRestaurant.PENDING.statusCode,
)

data class OrderUiState(
    val id: String = "",
    val image: String = "",
    val restaurantName: String = "Burger King",
    val date: String = "Jan 5",
)

data class OfferUiState(
    val id: String,
    val image: String,
)

data class RestaurantUiState(
    val id: String = "",
    val name: String = "",
    val rating: Double = 0.0,
    val priceLevel: PriceLevel = PriceLevel.LOW,
)

data class UserUiState(
    val username: String = "",
    val wallet: Double = 0.0,
    val currency: String = "",
)

fun Trip.toTaxiRideUiState(): TaxiRideUiState {
    return TaxiRideUiState(
        tripId = this.id,
        taxiColor = "White",
        taxiPlateNumber = this.taxiPlateNumber ?: "5874RE",
        rideStatus = this.tripStatus.statusCode,
        rideEstimatedTime = this.estimatedTimeToArriveInMints,
    )
}

fun Trip.toDeliveryOrderUiState(): DeliveryOrderUiState {
    return DeliveryOrderUiState(
        tripId = this.id,
        restaurantName = this.restaurantId ?: "Hello Restaurant",
    )
}

fun FoodOrder.toFoodOrderUiState(): FoodOrderUiState {
    return FoodOrderUiState(
        orderId = this.id,
        restaurantName = this.restaurantName,
        orderStatus = this.orderStatus.statusCode,
    )
}

fun TaxiRide.toTaxiRideUiState(): TaxiRideUiState {
    return TaxiRideUiState(
        tripId = this.id,
        taxiColor = "white", // missing taxi color
        taxiPlateNumber = this.taxiPlateNumber,
        rideStatus = this.tripStatus.statusCode,
        rideEstimatedTime = 30,
    )
}

fun DeliveryRide.toDeliveryOrderUiState(): DeliveryOrderUiState {
    return DeliveryOrderUiState(
        tripId = this.id,
        restaurantName = this.restaurantName,
        orderStatus = this.tripStatus.statusCode,
    )
}