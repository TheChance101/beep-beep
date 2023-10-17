package org.thechance.service_restaurant.data.collection.mapper

import org.thechance.service_restaurant.data.collection.CategoryCollection
import org.thechance.service_restaurant.data.collection.relationModels.CategoryDetails
import org.thechance.service_restaurant.domain.entity.Category

fun CategoryCollection.toEntity() = Category(
    id = id.toString(),
    name = name,
)

fun CategoryDetails.categoryDetailsToEntity() = Category(
    id = id.toString(),
    name = name,
    restaurants = restaurants.toEntity()
)

fun List<CategoryCollection>.toEntity(): List<Category> = map { it.toEntity() }

fun List<CategoryDetails>.categoryDetailsToEntity(): List<Category> = map { it.categoryDetailsToEntity() }

fun Category.toCollection() = CategoryCollection(name = name)

