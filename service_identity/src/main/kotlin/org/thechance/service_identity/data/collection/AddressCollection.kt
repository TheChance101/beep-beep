package org.thechance.service_identity.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import org.litote.kmongo.Id
import org.thechance.service_identity.domain.entity.Address

@Serializable
data class AddressCollection(
    @SerialName("_id")
    @BsonId
    @Contextual
    val id: ObjectId = ObjectId(),
    @SerialName("user_id")
    val userId: Id<UserCollection>,
    @SerialName("latitude")
    val latitude : Double,
    @SerialName("longitude")
    val longitude : Double,
    @SerialName("is_deleted")
    val isDeleted: Boolean = false
) {
    fun toAddress(): Address {
        return Address(
            id = id.toHexString(),
            userId = userId.toString(),
            latitude = latitude,
            longitude = longitude,
            isDeleted = isDeleted
        )
    }
}


