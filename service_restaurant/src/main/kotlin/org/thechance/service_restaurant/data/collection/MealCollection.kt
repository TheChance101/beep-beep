package org.thechance.service_restaurant.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

//TODO: remove "" from image after clean DB
@Serializable
data class MealCollection(
    @BsonId
    @Contextual
    @SerialName("_id")
    val id: ObjectId = ObjectId(),
    val name: String,
    val description: String,
    val isDeleted: Boolean = false,
    val price: Double,
    val currency: String,
    @Contextual
    val restaurantId: ObjectId,
    val cuisines: List<@Contextual ObjectId>,
    val image: String = ""
)



