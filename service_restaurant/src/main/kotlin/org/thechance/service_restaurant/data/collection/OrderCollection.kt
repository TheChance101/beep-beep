package org.thechance.service_restaurant.data.collection


import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import org.bson.codecs.pojo.annotations.BsonId
import java.util.*

data class OrderCollection(
    @BsonId
    @Contextual
    @SerialName("_id")
    val id: UUID = UUID.randomUUID(),
    @Contextual
    val userId: UUID,
    @Contextual
    val restaurantId: UUID,
    val meals: List<OrderMealCollection> = emptyList(),
    val totalPrice: Double,
    val createdAt: String,
    val orderStatus: Int
)

data class OrderMealCollection(
    @Contextual
    val mealId: UUID,
    val quantity: Int
)