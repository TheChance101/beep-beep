package org.thechance.service_restaurant.data.collection.relationModels

import kotlinx.serialization.Serializable
import org.thechance.service_restaurant.data.collection.CategoryCollection
import org.thechance.service_restaurant.data.collection.RestaurantCollection

@Serializable
data class CategoryRestaurant(
    val restaurants: MutableList<RestaurantCollection> = mutableListOf(),
    val categories: MutableList<CategoryCollection> = mutableListOf(),
)


