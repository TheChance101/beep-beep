package data.remote.mapper

import data.remote.model.MealDto
import domain.entity.Meal

fun List<MealDto>.toEntity(): List<Meal> = map { it.toEntity() }

fun MealDto.toEntity(): Meal {
    return Meal(
        id = id,
        restaurantId = restaurantId,
        name = name ?: "",
        description = description ?: "",
        price = price ?: 0.0,
        imageUrl = imageUrl ?: "",
        cuisines = emptyList(),
    )
}