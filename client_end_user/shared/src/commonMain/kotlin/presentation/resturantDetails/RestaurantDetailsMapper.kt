package presentation.resturantDetails

import domain.entity.Cuisine
import domain.entity.Meal
import domain.entity.Restaurant

fun Restaurant.toUIState() = RestaurantInfoUIState(
    id = id,
    name = name,
    address = address,
    rating = rate,
    priceLevel = priceLevel,
    image = "",
    discount = doubleToPercentage(1500.0),
    hasFreeDelivery = true,
    description = description,
)

fun Cuisine.toRestaurantCuisineUiState() = RestaurantCuisineUiState(
    id = id,
    name = name,
    meals = meals?.toUIState() ?: emptyList()
)

fun List<Cuisine>.toRestaurantCuisineUiState() = map { it.toRestaurantCuisineUiState() }

fun doubleToPercentage(value: Double): Int {
    return (value / 100.0).toInt()
}

fun Meal.toUIState() = MealUIState(
    id = id,
    name = name,
    restaurantName = restaurantName,
    price = price.value,
    totalPrice = price.value,
    image = imageUrl,
    currency = price.currency,
    quantity = 1,
    description = description,
)

fun List<Meal>.toUIState() = map { it.toUIState() }
