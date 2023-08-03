package org.thechance.service_restaurant.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import org.litote.kmongo.Id


@Serializable
data class RestaurantCollection(
    val name: String? = null,
    val description: String? = null,
    val priceLevel: String? = null,
    val rate: Double? = null,
    val phone: String? = null,
    val openingTime: String? = null,
    val closingTime: String? = null,
) {
    @BsonId
    @Contextual
    @SerialName("_id")
    val id: ObjectId = ObjectId()
    val isDeleted: Boolean = false
    val categoryIds: MutableList<@Contextual ObjectId> = mutableListOf()
    val addressIds: List<@Contextual Id<AddressCollection>> = emptyList()
    val mealIds: MutableList<@Contextual ObjectId> = mutableListOf()

}


