package org.thechance.service_taxi.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class TripCollection(
    @Contextual
    val driverId: ObjectId? = null,
    @Contextual
    val clientId: ObjectId? = null,
    val from: Pair<Long, Long>? = null,
    val to: Pair<Long, Long>? = null,
    val rate: Double? = null,
    val price: Double? = null,
    val isDeleted: Boolean? = null,
) {
    @SerialName("_id")
    @BsonId
    @Contextual
    val id: ObjectId = ObjectId()
}