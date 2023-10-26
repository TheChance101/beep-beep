package org.thechance.service_taxi.data.collection.relationModel

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import org.thechance.service_taxi.data.collection.LocationCollection
import org.thechance.service_taxi.data.collection.TaxiCollection

@Serializable
data class TripWithTaxi(
    @SerialName("_id")
    @BsonId
    @Contextual
    val id: ObjectId = ObjectId(),
    @Contextual
    val driverId: ObjectId? = null,
    @Contextual
    val clientId: ObjectId,
    @Contextual
    val orderId: ObjectId? = null,
    @Contextual
    val restaurantId: ObjectId? = null,
    val taxi: TaxiCollection,
    val startPoint: LocationCollection? = null,
    val destination: LocationCollection? = null,
    val startPointAddress: String,
    val destinationAddress: String,
    val rate: Double? = null,
    val price: Double?,
    val startDate: String? = null,
    val endDate: String? = null,
    val tripStatus: Int,
    val isATaxiTrip: Boolean
)
