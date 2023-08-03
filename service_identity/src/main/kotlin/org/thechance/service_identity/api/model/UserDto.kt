package org.thechance.service_identity.api.model

import kotlinx.serialization.Serializable
import org.thechance.service_identity.domain.entity.User

@Serializable
data class UserDto(
    val id: String? = null,
    val fullName: String? = null,
    val username: String? = null,
    val password: String? = null,
) {
    fun toUser(): User {
        return User(
            id = id,
            fullName = fullName,
            username = username,
            password = password,
        )
    }
}

@Serializable
data class DetailedUserDto(
    val id: String? = null,
    val fullName: String? = null,
    val username: String? = null,
    val password: String? = null,
    val email: String? = null,
    val walletId: String? = null,
    val addresses: List<String> = emptyList(),
    val permissions: List<String> = emptyList()
) {
    fun toUser(): User {
        return User(
            id = id,
            fullName = fullName,
            username = username,
            password = password,
            email = email,
            walletId = walletId,
            addresses = addresses,
            permissions = permissions
        )
    }
}

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
    val id: String? = null,
    val fullName: String? = null,
    val username: String? = null,
    val password: String? = null,
    val email: String? = null,
) {
    fun toUser(): User {
        return User(
            id = id,
            fullName = fullName,
            username = username,
            password = password,
            email = email
        )
    }
}