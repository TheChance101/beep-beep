package presentation.home

import domain.entity.InProgressWrapper
import domain.entity.PriceLevel
import presentation.cuisines.CuisineUiState

data class HomeScreenUiState(
    val offers: List<OfferUiState> = emptyList(),
    val recommendedCuisines: List<CuisineUiState> = emptyList(),
    val favoriteRestaurants: List<RestaurantUiState> = emptyList(),
    val inProgressWrapper: InProgressWrapper = InProgressWrapper(),
    val lastOrder: OrderUiState = OrderUiState(),
    val user: UserUiState = UserUiState(),
    val showCart: Boolean = false,
    val isLoggedIn: Boolean = false,
    val isMoreCuisine: Boolean = false,
    val isThereLastOrder: Boolean = false,
) {
    val maxCuisinesInHome: Int = 4
    private val maxOffersInBanal: Int = 3
    val hasProgress: Boolean
        get() = inProgressWrapper.taxisOnTheWay.isNotEmpty() ||
                inProgressWrapper.tripsOnTheWay.isNotEmpty() ||
                inProgressWrapper.ordersOnTheWay.isNotEmpty()

    fun getOfferImages(): List<String> {
        return this.offers.map { it.image }.take(maxOffersInBanal)
    }
}

data class OrderUiState(
    val id: String = "",
    val image: String = "",
    val restaurantName: String = "",
    val date: String = "",
)

data class OfferUiState(
    val id: String,
    val title: String,
    val image: String,
    val restaurants: List<RestaurantUiState>,
)

data class RestaurantUiState(
    val id: String = "",
    val name: String = "",
    val rating: Double = 0.0,
    val imageUrl: String = "",
    val priceLevel: PriceLevel = PriceLevel.LOW,
)

data class UserUiState(
    val username: String = "",
    val wallet: Double = 0.0,
    val currency: String = "",
)

