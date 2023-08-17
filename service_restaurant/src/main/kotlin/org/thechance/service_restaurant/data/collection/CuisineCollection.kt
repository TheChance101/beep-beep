package org.thechance.service_restaurant.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.util.*

@Serializable
data class CuisineCollection(
    @BsonId
    @Contextual
    @SerialName("_id")
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val isDeleted: Boolean = false,
    val meals: List<@Contextual UUID> = emptyList(),
)
