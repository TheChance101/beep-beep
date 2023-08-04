package org.thechance.service_restaurant.data.collection.mapper

import org.bson.types.ObjectId
import org.thechance.service_restaurant.data.collection.MealCollection
import org.thechance.service_restaurant.data.collection.MealDetailsCollection
import org.thechance.service_restaurant.domain.entity.Meal

fun Meal.toCollection(): MealCollection =
    MealCollection(
        name = name,
        description = description,
        price = price,
        cuisines = cuisines?.map { ObjectId(it.id) } ?: mutableListOf()
    )

fun MealCollection.toEntity(): Meal =
    Meal(
        id = id.toString(),
        name = name,
        description = description,
        price = price,
    )

fun List<MealCollection>.toEntity(): List<Meal> = this.map { it.toEntity() }

fun MealDetailsCollection.toEntity(): Meal {
    return Meal(
        id = id.toString(),
        name = name,
        description = description,
        price = price,
        cuisines = cuisines.toEntity()
    )
}
