package org.thechance.api_gateway.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Order(
    val id: String,
    val userId: String,
    val restaurantId: String,
    val meals: List<Meal>,
    val totalPrice: Double,
    val createdAt: Long,
    val orderStatus: Int = 0,
){
    @Serializable
    data class Meal(
        val mealId: String,
        val quantity: Int
    )
}