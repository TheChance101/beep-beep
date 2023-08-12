package org.thechance.service_identity.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import org.thechance.service_identity.endpoints.model.CreateLocationBody
import org.thechance.service_identity.endpoints.model.UpdateLocationBody

@Serializable
data class AddressCollection(
    @SerialName("_id")
    @BsonId
    @Contextual
    val id: ObjectId = ObjectId(),
    @SerialName("user_id")
    @Contextual
    val userId: ObjectId = ObjectId(),
    @SerialName("location")
    val location: LocationCollection ,
    @SerialName("is_deleted")
    val isDeleted: Boolean = false
)

@Serializable
data class CreateAddressDocument(
    @SerialName("_id")
    @Contextual
    val id: ObjectId = ObjectId(),
    val location: CreateLocationBody,
)

@Serializable
data class UpdateAddressDocument(
    val location: UpdateLocationBody? = null,
)