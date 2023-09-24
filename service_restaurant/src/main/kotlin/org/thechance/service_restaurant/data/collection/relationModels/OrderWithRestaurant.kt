package org.thechance.service_restaurant.data.collection.relationModels

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId
import org.thechance.service_restaurant.data.collection.OrderCollection
import org.thechance.service_restaurant.data.collection.RestaurantCollection

@Serializable
data class OrderWithRestaurant(
    @Contextual
    val id: ObjectId,
    val restaurant: RestaurantCollection,
    val meals: List<OrderCollection.MealCollection>,
    val totalPrice: Double,
    val createdAt: Long,
    val orderStatus: Int
)


