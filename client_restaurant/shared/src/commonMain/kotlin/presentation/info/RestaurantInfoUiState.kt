package presentation.info

import domain.entity.Location
import domain.entity.Restaurant
import presentation.base.ErrorState

data class RestaurantInfoUiState(
    val restaurant: RestaurantUiState = RestaurantUiState(),
    val isLoading: Boolean = false,
    val error: ErrorState? = null
)
data class RestaurantUiState(
    val id: String = "",
    val ownerId: String = "",
    val ownerUsername: String = "",
    val address: String = "",
    val rating: Double = 0.0,
    val priceLevel: String = "",
    val restaurantName: String = "",
    val phoneNumber: String = "",
    val openingTime: String = "",
    val closingTime: String = "",
    val description: String = ""
)

fun Restaurant.toUiState(): RestaurantUiState = RestaurantUiState(
    id = id,
    ownerId = ownerId,
    ownerUsername = ownerUsername,
    address = address,
    rating = rate,
    priceLevel = priceLevel,
    restaurantName = name,
    phoneNumber = phone,
    openingTime = openingTime,
    closingTime = closingTime,
    description = description
)

fun RestaurantUiState.toRestaurant() = Restaurant(
    id = id,
    ownerId = ownerId,
    location = Location(0.0, 0.0),
    ownerUsername = ownerUsername,
    rate = rating,
    priceLevel = priceLevel,
    name = restaurantName,
    phone = phoneNumber,
    openingTime = openingTime,
    closingTime = closingTime,
    description = description,
    address = address
)
