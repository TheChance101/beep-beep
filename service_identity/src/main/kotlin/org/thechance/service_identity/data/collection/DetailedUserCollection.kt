package org.thechance.service_identity.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import org.bson.codecs.pojo.annotations.BsonId
import java.util.UUID

@Serializable
data class DetailedUserCollection(
    @SerialName("_id")
    @BsonId
    @Contextual
    val id: UUID,
    val fullName: String,
    val username: String,
    val email: String,
    val isDeleted: Boolean = false,
    val details: List<UserDetailsCollection>
)