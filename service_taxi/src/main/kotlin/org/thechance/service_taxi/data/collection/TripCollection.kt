package org.thechance.service_taxi.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import java.util.UUID

@Serializable
data class TripCollection(
    @Contextual
    val taxiId: UUID? = null,
    @Contextual
    val driverId: UUID? = null,
    @Contextual
    val clientId: UUID?,
    val startPoint: LocationCollection? = null,
    val destination: LocationCollection? = null,
    val rate: Double? = null,
    val price: Double?,
    val startDate: String? = null,
    val endDate: String? = null,
) {
    @SerialName("_id")
    @BsonId
    @Contextual
    val id: UUID = UUID.randomUUID()
    val isDeleted: Boolean = false
}

