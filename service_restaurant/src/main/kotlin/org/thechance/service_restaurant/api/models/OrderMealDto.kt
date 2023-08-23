package org.thechance.service_restaurant.api.models

import kotlinx.serialization.Serializable

@Serializable
data class OrderMealDto(
    val mealId: String,
    val quantity: Int
)