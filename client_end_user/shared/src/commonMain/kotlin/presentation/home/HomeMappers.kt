package presentation.home

import domain.entity.DeliveryRide
import domain.entity.FoodOrder
import domain.entity.Offer
import domain.entity.Restaurant
import domain.entity.TaxiRide
import domain.entity.Trip
import domain.entity.User

fun Offer.toUiState() = OfferUiState(
    id = id,
    image = imageUrl,
    restaurants = restaurants.toRestaurantUiState(),
    title = title,
)

fun List<Offer>.toUiState()= map{it.toUiState()}

fun Restaurant.toRestaurantUiState() = RestaurantUiState(
    id = id,
    name = name,
    rating = rate,
    priceLevel = priceLevel,
    imageUrl = image
)

fun List<Restaurant>.toRestaurantUiState() = map { it.toRestaurantUiState() }


fun User.toUIState() = UserUiState(
    username = username,
    wallet = wallet.value,
    currency = wallet.currency,
)

fun Trip.toTaxiRideUiState(): TaxiRideUiState {
    return TaxiRideUiState(
        tripId = this.id,
        taxiColor = taxiColor,
        taxiPlateNumber = this.taxiPlateNumber ?: "",
        rideStatus = this.tripStatus.statusCode,
        rideEstimatedTime = this.estimatedTimeToArriveInMints,
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
        taxiColor = taxiColor,
        taxiPlateNumber = this.taxiPlateNumber,
        rideStatus = this.tripStatus.statusCode,
        rideEstimatedTime = 30,
    )
}

fun DeliveryRide.toDeliveryOrderUiState(): DeliveryRideUiState {
    return DeliveryRideUiState(
        tripId = this.id,
        restaurantName = this.restaurantName,
        orderStatus = this.tripStatus.statusCode,
    )
}