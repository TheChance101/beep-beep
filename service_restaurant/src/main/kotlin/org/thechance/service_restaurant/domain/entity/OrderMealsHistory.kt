package org.thechance.service_restaurant.domain.entity

import kotlinx.datetime.LocalDateTime

data class OrderMealsHistory(
    val id: String,
    val userId: String,
    val restaurantId: String,
    val restaurantName: String,
    val restaurantImage: String,
    val meals: List<Meal>,
    val totalPrice: Double,
    val createdAt: Long,
    val status: Int
) {
    data class Meal(
        val meadId: String,
//        val mealName: String,
        val quantity: Int
    )
}
