package org.thechance.service_restaurant.domain.entity

data class Cart(
    val id: String,
    val userId: String,
    val meals: List<Cart.Meal>,
    val totalPrice: Double,
    val restaurantId: String,
    val currency: String
){
    data class Meal(
        val mealId : String,
        val quantity : Int,
        val price: Double
    )
}
