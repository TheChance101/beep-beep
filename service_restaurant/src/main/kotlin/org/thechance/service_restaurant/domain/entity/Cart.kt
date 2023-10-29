package org.thechance.service_restaurant.domain.entity

data class Cart(
    val id: String,
    val userId: String,
    val restaurantId: String? = null,
    val restaurantImage: String? = null,
    val restaurantName: String? = null,
    val currency: String? = null,
    val meals: List<OrderedMeal>? = null,
    val totalPrice: Double = 0.0,
)

data class MealRequest(
    val userId: String,
    val restaurantId: String,
    val mealId: String,
    val quantity: Int,
)