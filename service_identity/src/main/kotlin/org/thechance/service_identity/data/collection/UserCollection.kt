package org.thechance.service_identity.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class UserCollection(
    @SerialName("_id")
    @BsonId
    @Contextual
    val id: ObjectId = ObjectId(),
    @SerialName("fullName")
    val fullName: String,
    @SerialName("username")
    val username: String,
    @SerialName("password")
    val password: String? = null,
    @SerialName("email")
    val email: String,
    @SerialName("permissions")
    val permissions: List<PermissionCollection> = emptyList(),
    @SerialName("isDeleted")
    val isDeleted: Boolean = false,
)

@Serializable
data class UpdateUserRequest(
    @SerialName("fullName")
    val fullName: String? = null,
    @SerialName("username")
    val username: String? = null,
    @SerialName("password")
    val password: String? = null,
    @SerialName("email")
    val email: String? = null,
)

@Serializable
data class CreateUserRequest(
    @SerialName("fullName")
    val fullName: String,
    @SerialName("username")
    val username: String,
    @SerialName("password")
    val password: String,
    @SerialName("email")
    val email: String,
)