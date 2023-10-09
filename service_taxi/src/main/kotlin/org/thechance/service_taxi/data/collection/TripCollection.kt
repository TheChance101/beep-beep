package org.thechance.service_taxi.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class TripCollection(
    @SerialName("_id")
    @BsonId
    @Contextual
    val id: ObjectId = ObjectId(),
    @Contextual
    val taxiId: ObjectId? = null,
    @Contextual
    val driverId: ObjectId? = null,
    @Contextual
    val clientId: ObjectId,
    val isDeleted: Boolean = false,
    val startPoint: LocationCollection? = null,
    val destination: LocationCollection? = null,
    val startPointAddress: String,
    val destinationAddress: String,
    val rate: Double? = null,
    val price: Double?,
    val startDate: String? = null,
    val endDate: String? = null,
    val isATaxiTrip: Boolean,
    val tripStatus: Int
)

