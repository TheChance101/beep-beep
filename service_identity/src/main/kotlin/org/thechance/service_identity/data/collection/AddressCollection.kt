package org.thechance.service_identity.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class AddressCollection(
    @SerialName("_id")
    @BsonId
    @Contextual
    val id: ObjectId = ObjectId(),
    @SerialName("user_id")
    @Contextual
    val userId: ObjectId = ObjectId(),
    @SerialName("latitude")
    val latitude : Double,
    @SerialName("longitude")
    val longitude : Double,
    @SerialName("is_deleted")
    val isDeleted: Boolean = false
)


