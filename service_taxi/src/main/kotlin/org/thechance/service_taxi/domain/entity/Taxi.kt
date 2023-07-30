package org.thechance.service_taxi.domain.entity

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class Taxi(
    val name: String?,
    val deleted: Boolean = false
) {
    @SerialName("_id")
    @BsonId
    @Contextual
    val id: ObjectId = ObjectId()
}
