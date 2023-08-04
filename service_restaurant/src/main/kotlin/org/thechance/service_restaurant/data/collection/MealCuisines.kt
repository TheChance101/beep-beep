package org.thechance.service_restaurant.data.collection

import kotlinx.serialization.Serializable


@Serializable
data class MealCuisines(
    val cuisines: List<CuisineCollection> = emptyList(),
    val meals: List<MealCollection> = emptyList()
)