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
    val id: String = UUID.randomUUID().toString(),
    @SerialName("user_id")
    @Contextual
    val userId: UUID,
    @SerialName("location")
    val location: LocationCollection ,
    @SerialName("is_deleted")
    val isDeleted: Boolean = false
)

