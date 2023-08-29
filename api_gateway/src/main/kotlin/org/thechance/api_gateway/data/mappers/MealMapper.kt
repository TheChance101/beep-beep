package org.thechance.api_gateway.data.mappers

import org.thechance.api_gateway.data.model.restaurant.MealResource
import org.thechance.api_gateway.endpoints.model.Meal

fun MealResource.toMeal(): Meal {
    return Meal(
        id = id,
        restaurantId = restaurantId,
        name = name,
        description = description,
        price = price,
        currency = currency,
        cuisines = cuisines
    )
}