package org.thechance.service_identity.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import org.thechance.service_identity.domain.util.Role


@Serializable
data class UserCollection(
    @SerialName("_id")
    @BsonId
    @Contextual
    val id: ObjectId = ObjectId(),
    val fullName: String,
    val username: String,
    val hashedPassword: String? = null,
    val email: String,
    val country: String? = null,
    val salt: String? = null,
    val permission: Int = Role.END_USER,
    val isDeleted: Boolean = false,
)
