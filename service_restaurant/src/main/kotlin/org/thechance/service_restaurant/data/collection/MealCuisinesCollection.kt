package org.thechance.service_restaurant.data.collection

import kotlinx.serialization.Serializable


@Serializable
data class MealCuisinesCollection(
    val meal_cuisines: List<CuisineCollection> = emptyList()
)