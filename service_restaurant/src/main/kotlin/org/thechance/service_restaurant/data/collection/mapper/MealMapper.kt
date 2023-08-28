package org.thechance.service_restaurant.data.collection.mapper

import org.thechance.service_restaurant.data.collection.MealCollection
import org.thechance.service_restaurant.data.collection.relationModels.MealWithCuisines
import org.thechance.service_restaurant.domain.entity.Meal
import org.thechance.service_restaurant.domain.entity.MealDetails
import java.util.*

fun MealDetails.toCollection(): MealCollection =
    MealCollection(
        name = name,
        restaurantId = UUID.fromString(restaurantId),
        description = description,
        price = price,
        currency = currency,
        cuisines = cuisines.map { cuisine ->
            UUID.fromString(cuisine.id)
        }
    )

fun MealCollection.toEntity() = Meal(
    id = id.toString(),
    restaurantId = restaurantId.toString(),
    name = name,
    description = description,
    price = price,
    currency = currency
)

fun MealWithCuisines.toEntity() = MealDetails(
    id = id.toString(),
    restaurantId = restaurantId.toString(),
    name = name,
    description = description,
    price = price,
    currency = currency,
    cuisines = cuisines.toEntity()
)

fun List<MealCollection>.toEntity(): List<Meal> = this.map { it.toEntity() }