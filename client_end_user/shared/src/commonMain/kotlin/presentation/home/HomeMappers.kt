package presentation.home

import domain.entity.Offer
import domain.entity.Restaurant
import domain.entity.User

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



fun User.toUIState() = UserUiState(
    username = name,
    wallet = walletValue,
    currency = currency,
    isLogin = true
)

