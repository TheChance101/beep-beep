package org.thechance.service_restaurant.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId


@Serializable
data class MealCollection(
    @BsonId
    @Contextual
    @SerialName("_id")
    val id: ObjectId = ObjectId(),
    val name: String? = null,
    val description: String? = null,
    val isDeleted: Boolean = false,
    val price: Int? = null,
    @Contextual
    val restaurantId: ObjectId = ObjectId(),
    val cuisines: List<@Contextual ObjectId> = emptyList(),
)



