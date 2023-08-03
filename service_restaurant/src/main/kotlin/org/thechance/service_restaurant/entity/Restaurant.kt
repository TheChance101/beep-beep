package org.thechance.service_restaurant.entity

import org.thechance.service_restaurant.api.models.RestaurantDto
import org.thechance.service_restaurant.data.collection.RestaurantCollection
import java.time.LocalTime

data class Restaurant(
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val priceLevel: String? = null,
    val rate: Double? = null,
    val phone: String? = null,
    val openingTime: String? = null,
    val closingTime: String? = null,
    val isDeleted: Boolean = false
//    val address: ObjectId,
//    val meals: List<ObjectId>,
)

