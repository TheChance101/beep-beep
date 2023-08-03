package org.thechance.service_restaurant.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class AddressCollection(
    val lat: Long? = null,
    val lon: Long? = null,
    val isDeleted: Boolean = false,
) {
    @BsonId
    @Contextual
    @SerialName("_id")
    val id: ObjectId = ObjectId()
}

