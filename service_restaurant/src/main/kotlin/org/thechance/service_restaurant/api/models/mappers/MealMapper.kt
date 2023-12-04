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
    currency = currency,
    cuisines = cuisines.toDto(),
    image = image
)

fun MealWithCuisineDto.toEntity() = MealDetails(
    id = id ?: "",
    restaurantName = restaurantName ?: "",
    restaurantId = restaurantId ?: "",
    name = name ?: "",
    description = description ?: "",
    price = price ?: NULL_DOUBLE,
    currency = currency ?: "",
    cuisines = cuisines?.map { Cuisine(id = it, name = "", image = image ?: "") } ?: emptyList(),
    image = image ?: ""
)

fun Meal.toDto() = MealWithCuisineDto(
    id = id,
    restaurantId = restaurantId,
    restaurantName = restaurantName,
    name = name,
    description = description,
    price = price,
    currency = currency,
    image = image
)

fun Meal.toMealDto() = MealDto(
    id = id,
    restaurantId = restaurantId,
    restaurantName = restaurantName,
    name = name,
    description = description,
    price = price,
    currency = currency,
    image = image
)

fun List<Meal>.toDto() = map { it.toDto() }

fun List<Meal>.toMealDto() = map { it.toMealDto() }

