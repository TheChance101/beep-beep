package presentation.home

import domain.entity.Offer
import domain.entity.Restaurant
import domain.entity.User

fun Offer.toUiState() = OfferUiState(
    id = id,
    image = imageUrl,
    restaurants = restaurants.toRestaurantUiState(),
    title = title,
)


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

