package org.thechance.service_taxi.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class TaxiCollection(
    val plateNumber: String? = null,
    val color: Long? = null,
    val type: String? = null,
    @Contextual
    val driverId: ObjectId? = null,
    val driverUsername: String? = null,
    val isAvailable: Boolean? = null,
    val seats: Int? = null,
    val tripsCount: Int? = null,
    @SerialName("_id")
    @BsonId
    @Contextual
    val id: ObjectId = ObjectId()
){
    val isDeleted: Boolean = false
}