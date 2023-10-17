package presentation.home

import domain.entity.FoodOrder
import domain.entity.PriceLevel
import domain.entity.Trip
import presentation.cuisines.CuisineUiState

data class HomeScreenUiState(
    val offers: List<OfferUiState> = emptyList(),
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
    val rideStatus: Int = 1,
    val rideEstimatedTime: Int = 30,
)

data class DeliveryOrderUiState(
    val tripId: String = "",
    val restaurantName: String = "",
)

data class FoodOrderUiState(
    val orderId: String = "",
    val restaurantName: String = "",
    val orderStatus: Int = 1,
)

data class OrderUiState(
    val id: String = "",
    val image: String = "https://s3-alpha-sig.figma.com/img/42f5/b4a1/acaee3b03ba74a8d32dc3ca923356221?Expires=1694995200&Signature=kU4mJsFvnFuSxvUemFrj3jWqT4Rq-Jclc8tq4hiZGhNNjAYEk2Dt1JYFzvy7EYlDgt36JwcC~PCgdqn53E3JkYKRAmojbgwtKN~Rn6o-z4GzgTEoUXvvS2TU6XJbS5Hmei1A3uD1qIavVsvd3Cr~P0KHXm3J8IPI14TwlywTqQLqcEuA9Htogw-cRFMhuscB6BIjMuL1v3Z-yITZSBsn~SEOU8MyhbpZrl7YFPh3bzOfUijlPwklTeygB8BPilmCVIkYPR86FMSzs~j4OXrKL9B7-T9thwqhS6Z4i0W2r~Qy0y7KAmX82NpHEK4Y7sn3jF4B3oxFTTtyEy-asku~~A__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4",
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
        taxiColor = this.taxiPlateNumber ?: "",
        taxiPlateNumber = this.taxiPlateNumber ?: "",
        rideStatus = this.tripStatus,
        rideEstimatedTime = this.estimatedTimeToArriveInMints,
    )
}

fun Trip.toDeliveryOrderUiState(): DeliveryOrderUiState {
    return DeliveryOrderUiState(
        tripId = this.id,
        restaurantName = this.restaurantId ?: "",
    )
}

fun FoodOrder.toFoodOrderUiState(): FoodOrderUiState {
    return FoodOrderUiState(
        orderId = this.id,
        restaurantName = this.restaurantName,
        orderStatus = this.orderStatus.statusCode,
    )
}