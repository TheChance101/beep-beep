package org.thechance.service_taxi.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import java.util.UUID

@Serializable
data class TaxiCollection(
    val plateNumber: String? = null,
    val color: Int? = null,
    val type: String? = null,
    @Contextual
    val driverId: UUID? = null,
    val isAvailable: Boolean? = null,
    val seats: Int? = null,
){
    @SerialName("_id")
    @BsonId
    @Contextual
    val id: UUID = UUID.randomUUID()
    val isDeleted: Boolean = false
}