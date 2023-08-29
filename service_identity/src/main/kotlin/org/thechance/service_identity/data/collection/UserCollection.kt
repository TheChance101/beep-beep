package org.thechance.service_identity.data.collection

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.thechance.service_identity.domain.util.Role
import java.util.*


@Serializable
data class UserCollection(
    @SerialName("_id")
    @BsonId
    @Contextual
    val id: UUID = UUID.randomUUID(),
    val fullName: String,
    val username: String,
    val hashedPassword: String? = null,
    val email: String,
    val salt: String? = null,
    val permission: Int = Role.END_USER,
    val isDeleted: Boolean = false,
)
