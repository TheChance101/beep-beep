package org.thechance.service_restaurant.api.models

import kotlinx.serialization.Serializable


@Serializable
data class OrderedMealDto(
    val mealId: String? = null,
    val name: String? = null,
    val image: String? = null,
    val quantity: Int? = null,
    val price: Double? = null
)
