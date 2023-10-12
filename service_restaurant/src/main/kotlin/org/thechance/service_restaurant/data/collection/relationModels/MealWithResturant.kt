package org.thechance.service_restaurant.data.collection.relationModels

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId
import org.thechance.service_restaurant.data.collection.RestaurantCollection
import org.thechance.service_restaurant.domain.entity.Meal

@Serializable
data class MealWithRestaurant(
    @Contextual
    val id: ObjectId,
    val restaurant: RestaurantCollection,
    val name: String,
    val description: String,
    val price: Double,
    val currency: String,
    val image: String,
)