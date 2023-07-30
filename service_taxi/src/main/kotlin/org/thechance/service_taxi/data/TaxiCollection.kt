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
    val plateNumber: String, // index
    val color: String,
    val type: String,
    @Contextual
    val driverId: ObjectId,
    val isDeleted: Boolean = false,
    val isAvailable: Boolean = true,
    val capacity: Int, // 1->normal etc..
)


// region mappers
fun TaxiCollection.toTaxi(): Taxi {
    return Taxi(
        id = id.toHexString(),
        plateNumber = plateNumber,
        color = color,
        type = type,
        driverId = driverId.toHexString(),
        isDeleted = isDeleted,
        isAvailable = isAvailable,
        capacity = capacity
    )
}

fun List<TaxiCollection>.toTaxes(): List<Taxi> = map(TaxiCollection::toTaxi)

fun Taxi.toCollection(): TaxiCollection {
    return TaxiCollection(
        id = ObjectId(id),
        plateNumber = plateNumber,
        color = color,
        type = type,
        driverId = ObjectId(driverId),
        isDeleted = isDeleted,
        isAvailable = isAvailable,
        capacity = capacity
    )
}
// endregion