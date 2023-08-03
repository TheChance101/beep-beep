package org.thechance.service_taxi.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class TaxiCollection(
    @SerialName("_id")
    @BsonId
    @Contextual
    val id: ObjectId = ObjectId(),
    val plateNumber: String? = null, // index
    val color: String? = null,
    val type: String? = null,
    @Contextual
    val driverId: ObjectId? = null,
    val isDeleted: Boolean? = null,
    val isAvailable: Boolean? = null,
    val capacity: Int? = null, // 1->normal etc..
)