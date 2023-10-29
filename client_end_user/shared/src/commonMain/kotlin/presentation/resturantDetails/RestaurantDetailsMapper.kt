package presentation.resturantDetails

import androidx.paging.map
import app.cash.paging.PagingData
import domain.entity.Cuisine
import domain.entity.Location
import domain.entity.Meal
import domain.entity.Restaurant
import domain.entity.Time
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun Restaurant.toUIState() = RestaurantInfoUIState(
    id = id,
    name = name,
    address = address,
    rating = rate,
    priceLevel = priceLevel,
    image = image,
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
    restaurantId = restaurantId,
    restaurantName = restaurantName,
    price = price.value,
    totalPrice = price.value,
    image = imageUrl,
    currency = price.currency,
    quantity = 1,
    description = description,
)

fun RestaurantInfoUIState.toRestaurant() = Restaurant(
    id = id,
    name = name,
    address = address,
    rate = rating,
    priceLevel = priceLevel,
    image = image,
    description = description,
    phone = "", // never used
    openingTime = Time(12, 0), // never used
    closingTime = Time(23, 0), // never used
    location = Location(0.0, 0.0), // never used
    ownerId = "", // never used
    ownerUsername = "" // never used
)

fun List<Meal>.toUIState() = map(Meal::toUIState)

fun Flow<PagingData<Meal>>.toUIState(): Flow<PagingData<MealUIState>> {
    return this.map { pagingData -> pagingData.map { it.toUIState() } }
}
