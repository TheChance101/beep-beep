package presentation.resturantDetails

data class RestaurantUIState(
    val restaurantInfoUIState: RestaurantInfoUIState,
    val isFavourite: Boolean,
)

data class RestaurantInfoUIState(
    val id: String,
    val name: String,
    val address: String,
    val rating: Double,
    val priceLevel: Int,
    val image: String,
    val deliveryPrice: Double,
    val discount: Double,
)