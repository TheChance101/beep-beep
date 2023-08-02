package org.thechance.service_restaurant.data.collection

import kotlinx.serialization.Serializable

@Serializable
data class CategoryRestaurantCollection(
    val restaurants: MutableList<RestaurantCollection> = mutableListOf()
)