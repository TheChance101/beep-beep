package org.thechance.service_restaurant.data.collection

import kotlinx.serialization.Serializable

@Serializable
data class CategoryRestaurant(
    val restaurants: MutableList<RestaurantCollection> = mutableListOf(),
    val categories:  MutableList<CategoryCollection> = mutableListOf(),
)