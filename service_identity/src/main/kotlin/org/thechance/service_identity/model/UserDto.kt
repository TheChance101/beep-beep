package org.thechance.service_identity.model

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.thechance.service_identity.entity.User

@Serializable
data class UserDto(
    @BsonId
    val id: Id<UserDto>? = null,
    val fullName: String,
    val username: String,
    val isDeleted: Boolean = false
)

fun UserDto.toUser(): User {
    return User(
        id = id.toString(),
        fullName = fullName,
        username = username,
        isDeleted = isDeleted
    )
}
