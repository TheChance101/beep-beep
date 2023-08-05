package org.thechance.service_restaurant.api.models.mappers

import org.thechance.service_restaurant.api.models.MealDetailsDto
import org.thechance.service_restaurant.api.models.MealDto
import org.thechance.service_restaurant.domain.entity.Cuisine
import org.thechance.service_restaurant.domain.entity.Meal
import org.thechance.service_restaurant.domain.entity.MealDetails

fun MealDetails.toDto() = MealDetailsDto(
    id = id,
    restaurantId = restaurantId,
    name = name,
    description = description,
    price = price,
    cuisines = cuisines.toDto()
)

fun MealDto.toEntity() = MealDetails(
    id = id ?: "",
    restaurantId = restaurantId ?: "",
    name = name ?: "",
    description = description ?: "",
    price = price ?: -1.0,
    cuisines = cuisines?.map { Cuisine(id = it, name = "") } ?: emptyList()
)

fun Meal.toDto() = MealDto(
    id = id,
    restaurantId = restaurantId,
    name = name,
    description = description,
    price = price
)

fun List<Meal>.toDto() = map { it.toDto() }


