package org.thechance.service_identity.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import java.util.*

@Serializable
data class UserCollection(
    @SerialName("_id")
    @BsonId
    @Contextual
    val id: String = UUID.randomUUID().toString(),
    @SerialName("fullName")
    val fullName: String,
    @SerialName("username")
    val username: String,
    @SerialName("password")
    val hashedPassword: String? = null,
    @SerialName("email")
    val email: String,
    @SerialName("salt")
    val salt: String? = null,
    @SerialName("permissions")
    val permissions: List<PermissionCollection> = emptyList(),
    @SerialName("isDeleted")
    val isDeleted: Boolean = false,
)
