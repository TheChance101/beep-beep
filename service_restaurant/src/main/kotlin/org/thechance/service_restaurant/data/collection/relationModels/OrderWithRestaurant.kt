package org.thechance.service_restaurant.data.collection.relationModels

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId
import org.thechance.service_restaurant.data.collection.OrderCollection
import org.thechance.service_restaurant.data.collection.RestaurantCollection
import org.thechance.service_restaurant.data.collection.mapper.toEntity
import org.thechance.service_restaurant.data.collection.mapper.toMealHistoryEntity
import org.thechance.service_restaurant.domain.entity.OrderMealsHistory

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


fun OrderWithRestaurant.toEntity(): OrderMealsHistory {
    return OrderMealsHistory(
        id = id.toString(),
        restaurantId = restaurant.id.toString(),
        restaurantName = restaurant.name,
        restaurantImage = restaurant.restaurantImage,
        totalPrice = totalPrice,
        createdAt = createdAt,
        status = orderStatus,
        meals = meals.toMealHistoryEntity()
    )
}

fun List<OrderWithRestaurant>.toEntity() = map { it.toEntity() }
