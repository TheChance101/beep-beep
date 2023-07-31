package org.thechance.service_identity.api.model

import kotlinx.serialization.Serializable
import org.thechance.service_identity.entity.User

@Serializable
data class UserDto(
    val id: String,
    val fullName: String,
    val username: String,
    val isDeleted: Boolean = false
) {
    fun toUser(): User {
        return User(
            id = id,
            fullName = fullName,
            username = username,
            isDeleted = isDeleted
        )
    }
}