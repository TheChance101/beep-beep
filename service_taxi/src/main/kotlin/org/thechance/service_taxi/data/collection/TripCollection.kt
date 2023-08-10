package org.thechance.service_taxi.data.collection

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class TripCollection(
    @Contextual
    val taxiId: ObjectId? = null,
    @Contextual
    val driverId: ObjectId? = null,
    @Contextual
    val clientId: ObjectId?,
    val startPoint: LocationCollection? = null,
    val destination: LocationCollection? = null,
    val rate: Double? = null,
    val price: Double?,
    val startDate: LocalDateTime? = null,
    val endDate: LocalDateTime? = null,
) {
    @SerialName("_id")
    @BsonId
    @Contextual
    val id: ObjectId = ObjectId()
    val isDeleted: Boolean = false
}

@Serializable
data class LocationCollection(
    val latitude: Double,
    val longitude: Double,
)