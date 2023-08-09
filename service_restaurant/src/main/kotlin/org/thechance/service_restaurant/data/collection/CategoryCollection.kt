package org.thechance.service_restaurant.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
@SerialName("categoryCollection")
data class CategoryCollection(
    val name: String,
    val restaurantIds: MutableList<@Contextual ObjectId> = mutableListOf()
) {
    @BsonId
    @Contextual
    @SerialName("_id")
    val id = ObjectId()
    val isDeleted: Boolean = false
}

