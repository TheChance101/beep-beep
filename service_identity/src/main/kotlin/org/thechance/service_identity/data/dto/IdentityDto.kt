package org.thechance.service_identity.data.dto

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

@Serializable
data class UserDto(
    @BsonId
    val id: Id<UserDto>,
    val fullName: String,
    val username: String,
    val isDeleted: Boolean = false
)
