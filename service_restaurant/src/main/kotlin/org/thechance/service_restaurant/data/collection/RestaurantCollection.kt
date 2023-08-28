package org.thechance.service_restaurant.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import java.util.UUID


@Serializable
data class RestaurantCollection(
    @Contextual
    val ownerId: UUID = UUID.randomUUID(),
    val name: String,
    val description: String?,
    val priceLevel: String?,
    val rate: Double?,
    val phone: String,
    val openingTime: String,
    val closingTime: String,
    val address: String,
    val location: LocationCollection,
    val currency:String
) {
    @BsonId
    @Contextual
    @SerialName("_id")
    val id: UUID = UUID.randomUUID()
    val isDeleted: Boolean = false
    val categoryIds: MutableList<@Contextual UUID> = mutableListOf()
    val cuisineIds: MutableList<@Contextual UUID> = mutableListOf()
    val mealIds: MutableList<@Contextual UUID> = mutableListOf()
}
