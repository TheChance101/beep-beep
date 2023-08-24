package presentation.info

import domain.entity.Address
import domain.entity.Restaurant

data class RestaurantInfoUiState(
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
    val description: String = "",
    val isLoading: Boolean = false
)

fun Restaurant.toUiState(): RestaurantInfoUiState = RestaurantInfoUiState(
    id = id,
    ownerId = ownerId,
    ownerUsername = ownerUsername,
    address = "paris, 123 street",
    rating = rate,
    priceLevel = priceLevel,
    restaurantName = name,
    phoneNumber = phone,
    openingTime = openingTime,
    closingTime = closingTime,
    description = description
)

fun RestaurantInfoUiState.toRestaurant() = Restaurant(
    id = id,
    ownerId = ownerId,
    address = Address(0.0, 0.0),
    ownerUsername = ownerUsername,
    rate = rating,
    priceLevel = priceLevel,
    name = restaurantName,
    phone = phoneNumber,
    openingTime = openingTime,
    closingTime = closingTime,
    description = description
)
