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
    @SerialName("full_name")
    val fullName: String,
    @SerialName("user_name")
    val username: String,
    @SerialName("password")
    val password: String,
    @SerialName("email")
    val email: String,
    @SerialName("isDeleted")
    val isDeleted: Boolean = false,
    @SerialName("details")
    val details: List<UserDetailsCollection>
)