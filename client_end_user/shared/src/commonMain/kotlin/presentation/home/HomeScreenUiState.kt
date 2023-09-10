package presentation.home

import domain.entity.InProgressWrapper
import domain.entity.PriceLevel

data class HomeScreenUiState(
    val offers: List<OfferUiState> = emptyList(),
    val recommendedCuisines: List<CuisineUiState> = emptyList(),
    val favoriteRestaurants: List<RestaurantUiState> = emptyList(),
    val inProgressWrapper: InProgressWrapper = InProgressWrapper(),
) {
    val hasProgress: Boolean
        get() = inProgressWrapper.taxisOnTheWay.isNotEmpty() ||
                inProgressWrapper.tripsOnTheWay.isNotEmpty() ||
                inProgressWrapper.ordersOnTheWay.isNotEmpty()

    fun getOfferImages(): List<String> {
        return this.offers.map { it.image }
    }
}

data class TripUiState(
    val id: String = "",
    val timeToArriveInMints: Int = 0,
)

data class OrderUiState(
    val id: String = "",
    val restaurantName: String,
)

data class TaxiUiState(
    val id: String = "",
    val color: String = "",
    val plate: String = "",
    val timeToArriveInMints: Int = 0,
)

data class CuisineUiState(
    val cuisineId: String = "",
    val cuisineName: String = "",
    val cuisineImageUrl: String = ""
)

data class OfferUiState(
    val id: String,
    val image: String
)


data class RestaurantUiState(
    val name: String = "",
    val rating: Double = 0.0,
    val priceLevel: PriceLevel = PriceLevel.LOW
)
