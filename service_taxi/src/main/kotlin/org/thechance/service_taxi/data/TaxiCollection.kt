package org.thechance.service_taxi.data

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import org.thechance.service_taxi.domain.entity.Taxi

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


// region mappers
fun TaxiCollection.toTaxi(): Taxi {
    return Taxi(
        id = id.toHexString(),
        plateNumber = plateNumber,
        color = color,
        type = type,
        driverId = driverId?.toHexString(),
        isDeleted = isDeleted ?: false,
        isAvailable = isAvailable ?: true,
        capacity = capacity
    )
}

fun List<TaxiCollection>.toTaxes(): List<Taxi> = map(TaxiCollection::toTaxi)

fun Taxi.toCollection(): TaxiCollection {
    return TaxiCollection(
        id = if (id.isBlank()) ObjectId() else ObjectId(id),
        plateNumber = plateNumber,
        color = color,
        type = type,
        driverId = if (driverId != null) ObjectId(driverId) else null,
        isDeleted = isDeleted,
        isAvailable = isAvailable,
        capacity = capacity
    )
}
// endregion