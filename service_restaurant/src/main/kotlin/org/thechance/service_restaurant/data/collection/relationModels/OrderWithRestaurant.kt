package org.thechance.service_restaurant.data.collection.relationModels

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId
import org.thechance.service_restaurant.data.collection.CartCollection
import org.thechance.service_restaurant.data.collection.RestaurantCollection

@Serializable
data class OrderWithRestaurant(
    @Contextual
    val id: ObjectId,
    @Contextual
    val userId: ObjectId,
    val restaurant: RestaurantCollection,
    val meals: List<CartCollection.MealCollection>,
    val totalPrice: Double,
    val createdAt: LocalDateTime,
    val orderStatus: Int
)