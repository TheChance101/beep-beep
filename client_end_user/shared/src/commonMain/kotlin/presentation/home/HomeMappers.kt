package presentation.home

import domain.entity.Offer
import domain.entity.Restaurant

fun Offer.toUiState(): OfferUiState {
    return OfferUiState(
        id = id,
        image = image
    )
}

fun Restaurant.toRestaurantUiState(): RestaurantUiState {
    return RestaurantUiState(
        name = name,
        rating = rate,
        priceLevel = priceLevel
    )
}

fun List<Restaurant>.toRestaurantUiState() = map { it.toRestaurantUiState() }
