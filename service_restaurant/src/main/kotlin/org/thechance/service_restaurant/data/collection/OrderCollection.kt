package org.thechance.service_restaurant.data.collection


import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import java.util.*

@Serializable
data class OrderCollection(
    @BsonId
    @Contextual
    @SerialName("_id")
    val id: UUID,
    @Contextual
    val userId: UUID,
    @Contextual
    val restaurantId: UUID,
    val meals: List<OrderMealCollection> = emptyList(),
    val totalPrice: Double,
    val createdAt: Long,
    val orderStatus: Int
)

@Serializable
data class OrderMealCollection(
    @Contextual
    val mealId: UUID,
    val quantity: Int
)