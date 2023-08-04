package org.thechance.service_restaurant.data.collection

import kotlinx.serialization.Serializable

@Serializable
data class RestaurantCuisine(
    val cuisines: MutableList<CuisineCollection> = mutableListOf(),
)