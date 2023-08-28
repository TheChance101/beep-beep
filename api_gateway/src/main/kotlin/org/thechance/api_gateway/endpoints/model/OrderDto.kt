package org.thechance.api_gateway.endpoints.model

import kotlinx.serialization.Serializable

@Serializable
data class OrderDto(
    val id: String,
    val userId: String,
    val restaurantId: String,
    val meals: List<OrderMealDto>,
    val totalPrice: Double,
    val createdAt: Long,
    val orderStatus: Int,
){
    @Serializable
    data class OrderMealDto(
        val mealId: String,
        val quantity: Int
    )
}