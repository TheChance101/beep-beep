package org.thechance.service_restaurant.data.collection


import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class OrderCollection(
    @BsonId
    @Contextual
    @SerialName("_id")
    val id: ObjectId,
    @Contextual
    val userId: ObjectId,
    @Contextual
    val restaurantId: ObjectId,
    val meals: List<CartCollection.MealCollection> = emptyList(),
    val totalPrice: Double,
    val createdAt: LocalDateTime,
    val orderStatus: Int
)