package org.thechance.service_identity.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class DetailedUser(
    @SerialName("_id")
    @BsonId
    @Contextual
    val id: ObjectId,
    val fullName: String,
    val username: String,
    val email: String,
    val phone: String,
    val isDeleted: Boolean = false,
    val details: List<UserDetailsCollection>
)