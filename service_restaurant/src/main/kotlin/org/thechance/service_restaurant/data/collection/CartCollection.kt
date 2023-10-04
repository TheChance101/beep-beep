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
    @Contextual
    val restaurantId: ObjectId? = null,
    val meals: List<MealCollection> = mutableListOf(),
) {
    @Serializable
    data class MealCollection(
        @Contextual
        val mealId: ObjectId,
        val name: String = "",
        val image: String = "",
        val quantity: Int,
        val price: Double
    )
}

