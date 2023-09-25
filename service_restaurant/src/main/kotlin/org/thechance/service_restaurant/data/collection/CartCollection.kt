package org.thechance.service_restaurant.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class CartCollection(
    @BsonId
    @Contextual
    @SerialName("_id")
    val id: ObjectId = ObjectId(),
    @Contextual
    val userId: ObjectId,
    val meals: List<MealCollection> = emptyList(),
    val totalPrice: Double = 0.0,
    @Contextual
    val restaurantId: ObjectId ?= null,
    val currency: String? = null,
){
    @Serializable
    data class MealCollection(
        @Contextual
        val mealId: ObjectId,
        val quantity: Int,
        val price: Double
    )
}