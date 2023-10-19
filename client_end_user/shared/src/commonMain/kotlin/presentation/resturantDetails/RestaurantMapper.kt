package presentation.resturantDetails

import domain.entity.Location
import domain.entity.Meal
import domain.entity.Restaurant
import domain.entity.Time

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

fun RestaurantInfoUIState.toRestaurant() = Restaurant(
    id = id,
    name = name,
    address = address,
    rate = rating,
    priceLevel = priceLevel,
    image = image,
    description = description,
    phone = "", // never used
    openingTime = Time(12,0), // never used
    closingTime = Time(23,0), // never used
    location = Location(0.0,0.0), // never used
    ownerId = "", // never used
    ownerUsername = "" // never used
)

fun doubleToPercentage(value: Double): Int {
    return (value / 100.0).toInt()
}