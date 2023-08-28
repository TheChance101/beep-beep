package org.thechance.service_restaurant.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import java.util.*


@Serializable
data class MealCollection(
    @BsonId
    @Contextual
    @SerialName("_id")
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val description: String,
    val isDeleted: Boolean = false,
    val price: Double,
    val currency: String,
    @Contextual
    val restaurantId: UUID = UUID.randomUUID(),
    val cuisines: List<@Contextual UUID>,
)



