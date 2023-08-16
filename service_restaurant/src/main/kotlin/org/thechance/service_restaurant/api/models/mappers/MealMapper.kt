package org.thechance.service_restaurant.api.models.mappers

import org.thechance.service_restaurant.api.models.MealDetailsDto
import org.thechance.service_restaurant.api.models.MealDto
import org.thechance.service_restaurant.api.models.MealWithCuisineDto
import org.thechance.service_restaurant.domain.entity.Cuisine
import org.thechance.service_restaurant.domain.entity.Meal
import org.thechance.service_restaurant.domain.entity.MealDetails
import org.thechance.service_restaurant.domain.utils.Validation.Companion.NULL_DOUBLE

fun MealDetails.toDto() = MealDetailsDto(
    id = id,
    restaurantId = restaurantId,
    name = name,
    description = description,
    price = price,
    cuisines = cuisines.toDto()
)

fun MealWithCuisineDto.toEntity() = MealDetails(
    id = id ?: "",
    restaurantId = restaurantId ?: "",
    name = name ?: "",
    description = description ?: "",
    price = price ?: NULL_DOUBLE,
    cuisines = cuisines?.map { Cuisine(id = it, name = "") } ?: emptyList()
)

fun Meal.toDto() = MealWithCuisineDto(
    id = id,
    restaurantId = restaurantId,
    name = name,
    description = description,
    price = price
)

fun Meal.toMealDto() = MealDto(
    id = id,
    restaurantId = restaurantId,
    name = name,
    description = description,
    price = price,
)

fun List<Meal>.toDto() = map { it.toDto() }

fun List<Meal>.toMealDto() = map { it.toMealDto() }

