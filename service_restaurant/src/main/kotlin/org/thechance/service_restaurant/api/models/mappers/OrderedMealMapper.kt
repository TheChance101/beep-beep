package org.thechance.service_restaurant.api.models.mappers

import org.thechance.service_restaurant.api.models.OrderedMealDto
import org.thechance.service_restaurant.domain.entity.OrderedMeal

fun OrderedMeal.toDto() = OrderedMealDto(
    mealId = meadId,
    quantity = quantity,
    image = image,
    name = name,
    price = price
)

fun List<OrderedMeal>.toDto() = map { it.toDto() }
