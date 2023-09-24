package data.remote.mapper

import data.remote.model.MealDto
import domain.entity.Meal

fun MealDto.toEntity(): Meal {
    return Meal(
        id = id ?: "",
        name = name?: "",
        description = description ?: "",
        price = price ?: 0.0,
        currency = currency ?: "",
        restaurantId = restaurantId ?: "",
        cuisines = cuisines ?: emptyList(),
        image = image ?: ""
    )
}