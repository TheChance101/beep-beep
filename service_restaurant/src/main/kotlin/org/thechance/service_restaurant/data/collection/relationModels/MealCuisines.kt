package org.thechance.service_restaurant.data.collection.relationModels

import kotlinx.serialization.Serializable
import org.thechance.service_restaurant.data.collection.CuisineCollection
import org.thechance.service_restaurant.data.collection.MealCollection


@Serializable
data class MealCuisines(
    val cuisines: List<CuisineCollection> = emptyList(),
    val meals: List<MealCollection> = emptyList()
)