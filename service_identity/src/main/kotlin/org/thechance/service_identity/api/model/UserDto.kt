package org.thechance.service_identity.api.model

import kotlinx.serialization.Serializable
import org.thechance.service_identity.domain.entity.User

@Serializable
data class UserDto(
    val id: String? = null,
    val fullName: String,
    val username: String,
) {
    fun toUser(): User {
        return User(
            id = id,
            fullName = fullName,
            username = username,
        )
    }
}