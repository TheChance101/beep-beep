package org.thechance.service_identity.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import java.util.UUID


@Serializable
data class AddressCollection(
    @SerialName("_id")
    @BsonId
    @Contextual
    val id: UUID = UUID.randomUUID(),
    @Contextual
    val userId: UUID,
    val location: LocationCollection ,
    val isDeleted: Boolean = false
)

