package org.thechance.service_restaurant.data.collection.mapper

import org.thechance.service_restaurant.data.collection.CategoryCollection
import org.thechance.service_restaurant.domain.entity.Category

fun CategoryCollection.toEntity() = Category(
    id = id.toString(),
    name = name,
)

fun List<CategoryCollection>.toEntity(): List<Category> = map { it.toEntity() }


fun Category.toCollection() = CategoryCollection(name = name)
