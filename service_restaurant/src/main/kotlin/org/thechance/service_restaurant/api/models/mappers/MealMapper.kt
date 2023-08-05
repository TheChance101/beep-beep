package org.thechance.service_restaurant.api.models.mappers

import org.thechance.service_restaurant.api.models.MealDto
import org.thechance.service_restaurant.domain.entity.Meal

fun Meal.toDto(): MealDto =
    MealDto(
        id = id,
        restaurantId = restaurantId,
        name = name,
        description = description,
        price = price,
        cuisines = cuisines.toDto()
    )

fun List<Meal>.toDto(): List<MealDto> = this.map { it.toDto() }

fun MealDto.toEntity(): Meal =
    Meal(
        id = id ?: "",
        restaurantId = restaurantId,
        name = name,
        description = description,
        price = price
    )


