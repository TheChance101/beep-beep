package org.thechance.service_restaurant.domain.entity

data class MealDetails(
    val id: String,
    val restaurantId: String,
    val name: String,
    val description: String,
    val price: Double,
    val currency:String,
    val cuisines: List<Cuisine>,
    val image : String
)
