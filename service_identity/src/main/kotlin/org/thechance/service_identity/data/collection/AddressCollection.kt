package org.thechance.service_identity.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.util.UUID


@Serializable
data class AddressCollection(
    @SerialName("_id")
    @BsonId
    @Contextual
    val id: UUID = UUID.randomUUID(),
    @SerialName("user_id")
    @Contextual
    val userId: ObjectId,
    @SerialName("location")
    val location: LocationCollection ,
    @SerialName("is_deleted")
    val isDeleted: Boolean = false
)

