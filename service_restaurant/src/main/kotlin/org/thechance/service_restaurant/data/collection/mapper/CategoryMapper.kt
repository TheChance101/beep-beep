package org.thechance.service_restaurant.data.collection.mapper

import org.thechance.service_restaurant.data.collection.CategoryCollection
import org.thechance.service_restaurant.entity.Category

fun CategoryCollection.toEntity() = Category(
    id = id.toString(),
    name = name,
    isDeleted = isDeleted,
)

fun List<CategoryCollection>.toEntity(): List<Category> = map { it.toEntity() }


fun Category.toCollection() = CategoryCollection(name = name)
