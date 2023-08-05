package org.thechance.service_restaurant.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import org.litote.kmongo.Id


@Serializable
data class RestaurantCollection(
    @Contextual
    val ownerId: ObjectId,
    val name: String,
    val description: String,
    val priceLevel: String,
    val rate: Double,
    val phone: String,
    val openingTime: String,
    val closingTime: String,
) {
    @BsonId
    @Contextual
    @SerialName("_id")
    val id: ObjectId = ObjectId()
    val isDeleted: Boolean = false
    val categoryIds: MutableList<@Contextual ObjectId> = mutableListOf()
    val addressIds: List<@Contextual Id<AddressCollection>> = emptyList()
    val cuisineIds: MutableList<@Contextual ObjectId> = mutableListOf()
    val mealIds: MutableList<@Contextual ObjectId> = mutableListOf()
}
