package org.thechance.service_restaurant.data.collection


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
    val meals: List<MealCollection> = emptyList(),
    val totalPrice: Double,
    val createdAt: Long,
    val orderStatus: Int
){
    @Serializable
    data class MealCollection(
        @Contextual
        val mealId: ObjectId,
        val quantity: Int
    )
}

