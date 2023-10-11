package org.thechance.service_restaurant.data.collection.mapper

import org.thechance.service_restaurant.data.collection.CuisineCollection
import org.thechance.service_restaurant.domain.entity.Cuisine

fun Cuisine.toCollection(): CuisineCollection = CuisineCollection(name = name, image = image?:"")

fun CuisineCollection.toEntity(): Cuisine = Cuisine(id = id.toString(), name = name,image = image)

fun List<CuisineCollection>.toEntity(): List<Cuisine> = this.map { it.toEntity() }