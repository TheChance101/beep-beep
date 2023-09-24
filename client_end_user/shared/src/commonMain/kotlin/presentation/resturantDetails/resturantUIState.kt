package presentation.resturantDetails

import domain.entity.Meal

data class RestaurantUIState(
    val restaurantInfoUIState: RestaurantInfoUIState = RestaurantInfoUIState(),
    val isFavourite: Boolean = false,
    val mostOrders :List<MealUIState> = emptyList(),
    val sweets :List<MealUIState> = emptyList(),
)

data class MealUIState(
    val id: String = "",
    val name: String = "",
    val price: Double = 0.0,
    val image: String = "",
    val discount: Double = 0.0,
)

fun Meal.toUIState() = MealUIState(
    id = id,
    name = name,
    price = price,
    image = image,
)

data class RestaurantInfoUIState(
    val id: String = "",
    val name: String = "",
    val address: String = "",
    val rating: Double = 0.0,
    val priceLevel: Double = 0.0,
    val image: String = "",
    val deliveryPrice: Double = 0.0,
    val discount: Double = 0.0,
)