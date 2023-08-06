package org.thechance.service_restaurant.data.collection.relationModels

import kotlinx.serialization.Serializable
import org.thechance.service_restaurant.data.collection.CuisineCollection

@Serializable
data class RestaurantCuisine(
    val cuisines: MutableList<CuisineCollection> = mutableListOf(),
)