package org.thechance.service_restaurant.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class MealDetailsCollection(
    @BsonId
    @Contextual
    @SerialName("_id")
    val id: ObjectId = ObjectId(),
    @Contextual
    val restaurantId: ObjectId,
    val name: String,
    val description: String,
    val isDeleted: Boolean = false,
    val price: Double,
    val cuisines: List<CuisineCollection> = emptyList(),
)