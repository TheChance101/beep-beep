package org.thechance.service_identity.api.model

import kotlinx.serialization.Serializable
import org.thechance.service_identity.domain.entity.User

@Serializable
data class UserDto(
    val id: String? = null,
    val fullName: String? = null,
    val username: String? = null,
    val password: String? = null,
)

@Serializable
data class CreateUserRequest(
    val fullName: String,
    val username: String,
    val password: String
) {
    fun toUser(): User {
        return User(
            fullName = fullName,
            username = username,
            password = password
        )
    }
}

@Serializable
data class UpdateUserRequest(
    val fullName: String? = null,
    val username: String? = null,
    val password: String? = null,
    val email: String? = null,
) {
    fun toUser(): User {
        return User(
            fullName = fullName,
            username = username,
            password = password,
            email = email
        )
    }
}