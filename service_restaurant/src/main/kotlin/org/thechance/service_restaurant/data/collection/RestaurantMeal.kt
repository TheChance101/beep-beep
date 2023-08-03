package org.thechance.service_restaurant.data.collection

import kotlinx.serialization.Serializable

@Serializable
data class RestaurantMeal(
    val meals: MutableList<MealCollection> = mutableListOf(),
)